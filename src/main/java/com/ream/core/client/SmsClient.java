package com.ream.core.client;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//
@FeignClient(name = "smsClient", url = "${sms-panel.api-url}")
@Headers({"cache-control : no-cache ", "content-type: application/json"})
public interface SmsClient {


    @PostMapping("/sendsms")
    ResponseEntity<String> sendMassages(@RequestParam String username, @RequestParam String password, @RequestParam String source, @RequestParam String destination, @RequestParam String message);


    @PostMapping("/msgstatus")
    ResponseEntity<String> findMassageStatus(@RequestParam String username, @RequestParam String password, @RequestParam String msgid);


    @PostMapping("/getcredit")
    ResponseEntity<String> getCredit(@RequestParam String username, @RequestParam String password);
}
