package com.ream.core.service.voting;


import com.google.gson.Gson;
import com.ream.core.client.SmsClient;
import com.ream.core.service.voting.dto.SmsLogDto;
import com.ream.core.service.voting.dto.SmsStatusDto;
import com.ream.core.service.security.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@EnableScheduling
@EnableAsync
public class SmsService {
    @Autowired
    SmsClient smsClient;


    @Autowired
    UserService userService;

    @Value("${sms-panel.username}")
    private String userName;

    @Value("${sms-panel.password}")
    private String password;

    @Value("${sms-panel.source}")
    private String source;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Autowired
    SmsLogService smsLogService;

    @Transactional
    @Async
    public void sendMassages(String user, String destination, String message) {
        List<String> stringList = new ArrayList<>();
        Boolean isValid = isValidPhoneNumber(destination);
        if (isValid) {
            try {
                ResponseEntity<String> response = smsClient.sendMassages(this.userName, this.password, this.source, destination, message);
                if (response.getStatusCode().equals(HttpStatus.OK)) {
                    Gson gson = new Gson();
                    String[] st = gson.fromJson(response.getBody(), String[].class);
                    stringList = Arrays.asList(st);
                    List<SmsLogDto> smsLogDtos = saveSmsLog(user, stringList, destination, message);
                }

            } catch (Exception e) {

            }
        }

        return ;
    }


    public Optional<List<SmsStatusDto>> findMassageStatus(String msgid) {

        List<SmsStatusDto> smsStatusDtoList = new ArrayList<>();
        try {
            ResponseEntity<String> response = smsClient.findMassageStatus(this.userName, this.password, msgid);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                Gson gson = new Gson();
                SmsStatusDto[] st = gson.fromJson(response.getBody(), SmsStatusDto[].class);
                smsStatusDtoList = Arrays.asList(st);

            }

        } catch (Exception e) {

        }

        return smsStatusDtoList.size() > 0 ? Optional.ofNullable(findStep(smsStatusDtoList)) : Optional.empty();
    }


    public boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "09\\d[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        if (phoneNumber.length() == 11) {
            return matcher.matches();
        } else return false;

    }

    public Optional<String> getCredit() {
        String reSt = "";
        try {
            ResponseEntity<String> response = smsClient.getCredit(this.userName, this.password);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                reSt = response.getBody();
            }
        } catch (Exception e) {
        }
        return !reSt.trim().isEmpty() ? Optional.ofNullable(reSt) : Optional.empty();
    }


    private List<SmsStatusDto> findStep(List<SmsStatusDto> dtos) {
        if (dtos.size() > 0) {
            for (SmsStatusDto statusDto : dtos) {
                statusDto.setPersianStep(findPersianStep(statusDto.getStatus()));
            }

        }
        return dtos;
    }

    private String findPersianStep(String step) {
        String perStep = "";
        switch (step) {
            case "1":
                perStep = "پیام مورد نظر یافت نشد/در صف ارسال به مخابرات  ";
                break;
            case "2":
                perStep = "به مخابرات تحویل داده شد و شناسه پیام دریافت شد ";
                break;
            case "4":
                perStep = "در حال ارسال به مخابرات  ";
                break;
            case "5":
                perStep = "به مقصد تحویل داده نشد ";
                break;
            case "6":
                perStep = "به مقصد تحویل داده شد";
                break;
            case "7":
                perStep = "مخابرات پاسخی مبنی بر پذیرش پیام ارائه نداد ";
                break;
            case "8":
                perStep = " مخابرات پیام را مردود اعالم کرد ";
                break;
            case "9":
                perStep = " درحال تحویل دادن به مقصد";
                break;
            case "10":
                perStep = "بخشی از پیام به مخابرات تحویل داده شد ";
                break;
            case "11":
                perStep = "یافت نشد MsgID ";
                break;
            case "12":
                perStep = "بخشی از پیام به مقصد تحویل داده شد ";
                break;
            case "13":
                perStep = "تحویل داده نشد ";
                break;

            case "52":
                perStep = "وضعیت نامشخص و نمیدانم ";
                break;
            default:
                perStep = "وضعیت تعریف نشده است";
                break;

        }
        return perStep;

    }

    @Transactional
    public List<SmsLogDto> saveSmsLog(String user, List<String> list, String destination, String message) {
        List<SmsLogDto> smsLogDtos = new ArrayList<>();

        try {


            if (list.size() > 0) {
                list.stream().forEach(f -> {
                    SmsLogDto smsLogDto = new SmsLogDto();
                    smsLogDto.setDestination(destination);
                    smsLogDto.setMessage(message);
                    smsLogDto.setMsgId(f);
                    smsLogDto.setUserBy(userService.getUserByUserName(user).getId());
                    smsLogDto.setStatus(-1);
                    smsLogDto = smsLogService.save(smsLogDto);
                    smsLogDtos.add(smsLogDto);
                });


            }
        } catch (Exception e) {

        }
        return smsLogDtos;

    }

    @Transactional
    @Scheduled(cron = "0 * * * * *")
    public void updateSmsLog() {
        List<SmsLogDto> smsLogDtos = smsLogService.findSmsNoStatus();
        if (smsLogDtos.size() > 0) {
            smsLogDtos.stream().forEach(f -> {
                Optional<List<SmsStatusDto>> opSmsStatusDtos = findMassageStatus(f.getMsgId());
                if (opSmsStatusDtos.isPresent() ) {
                    f.setStatus(Integer.valueOf(Objects.nonNull(opSmsStatusDtos.get().get(0).getStatus()) ? opSmsStatusDtos.get().get(0).getStatus() :"-1"));
                    f.setPersianStep(opSmsStatusDtos.get().get(0).getPersianStep());
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        f.setSendTime(formatter.parse(opSmsStatusDtos.get().get(0).getSendTime()));
                    } catch (Exception e) {

                    }
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        f.setDeliverTime(formatter.parse(opSmsStatusDtos.get().get(0).getDeliverTime()));
                    } catch (Exception e) {
                    }
                }
                smsLogService.update(f);
            });

        }

    }

}