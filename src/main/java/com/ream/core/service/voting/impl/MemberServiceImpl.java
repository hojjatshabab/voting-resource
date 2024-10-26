package com.ream.core.service.voting.impl;

import com.ream.core.domain.voting.Member;
import com.ream.core.repository.voting.MemberRepository;
import com.ream.core.service.JalaliCalendar;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.UserService;
import com.ream.core.service.voting.MemberService;
import com.ream.core.service.voting.SmsService;
import com.ream.core.service.voting.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.ream.core.service.voting.mapper.MemberDtoMapper;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Value("${update.clock}")
    private String clock;

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    @Autowired
    MemberDtoMapper memberDtoMapper;


    @Override
    public MemberDto save(MemberDto memberDto) {
        return null;
    }

    @Override
    public MemberDto update(MemberDto memberDto) {
        return null;
    }

    @Override
    public PageResponse<MemberDto> findAll(PageRequest<MemberDto> model) {
        return null;
    }

    @Override
    public PageResponse<MemberDto> findAllNationalCode(String nationalCode, PageRequest<MemberDto> model) {
/*
        ImmutablePair<Long, List<Member>> result = memberRepository.findAllByNationalCode(nationalCode, model);
        long count = result.getLeft();
        List<MemberDto> memberDtoList = setFinalityOrders(memberDtoMapper.toDtoList(result.getRight()));

        return new PageResponse<>(memberDtoList, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());

*/
        return null;
    }

    @Override
    public List<MemberDto> findAll() {
        List<Member> list = memberRepository.findAll();
        if (list.size() > 0) {
            return memberDtoMapper.toDtoList(list);
        }
        return new ArrayList<>();
    }

    @Override
    public Optional<List<MemberDto>> findByNationalCodeContain(String nationalCode) {
        return Optional.empty();
    }

    @Override
    public Optional<MemberDto> findById(UUID id) {
        return Optional.empty();
    }


    @Override
    public Boolean deleteById(UUID id) {
        if (id == null || !findById(id).isPresent()) {
            return false;
        }
        memberRepository.deleteById(id);
        return true;
    }

    @Override
    public Boolean finalityChoiceMemberById(UUID id) {
        return null;
    }


    @Autowired
    private SmsService smsService;


    @Autowired
    UserService userService;

    @Override
    public Optional<List<MemberDto>> getMembersByCurrentUser() {
        /*String nationalCode = userService.getCurrentUser().getUsername();
        Optional<List<Member>> optionalMembers = memberRepository.findByNationalCodeIgnoreCaseContainingOrderByContractNumberAsc(nationalCode);
        if (optionalMembers.get().size() > 0) {
            return Optional.ofNullable(setFinalityOrders(memberDtoMapper.toDtoList(optionalMembers.get())));
        }*/
        return Optional.empty();
    }

    @Override
    public Optional<List<MemberDto>> findAllMember(UUID memberId) {
        return Optional.empty();
    }

   /* @Override
    public Boolean removeUnitSelection(MemberDto memberDto, Optional<List<UnitSelectionDto>> unitSelectionDtoList) {
        if (unitSelectionDtoList.isPresent()) {
            for (UnitSelectionDto u : unitSelectionDtoList.get()) {
                if (Objects.nonNull(u.getId())) {
                    unitSelectionRepository.deleteById(u.getId());
                }
            }
        }
        Optional<Member> member = memberRepository.findById(memberDto.getId());
        Optional<MemberCondition> memberCondition = memberConditionRepository.findByMember(member.get());
        if (memberCondition.isPresent()) {
            List<MemberConditionOrder> memberConditionOrders =
                    memberConditionOrderRepository.findByMemberCondition(memberCondition.get());
            for (MemberConditionOrder m : memberConditionOrders) {
                if (Objects.nonNull(m.getId())) {
                    memberConditionOrderRepository.delete(m);
                }
            }
            memberConditionRepository.delete(memberCondition.get());
        }
        List<MemberAggregation> memberAggregations = memberAggregationRepository.findByMaster(member.get());
        memberAggregations.stream().forEach(m -> {
            Member mMember = m.getMember();
            mMember.setFinalityChoice(false);
            mMember.setConfirmSelection(false);
            mMember.setConfirmChoiceDate(null);
            mMember.setIdCode(null);
            mMember.setConfirmCode(null);
            memberRepository.save(mMember);
            StringBuilder builder5 = new StringBuilder();
            builder5.append("*سامانه سرا بنیاد تعاون آجا*").append("\n")
                    .append("انتخاب واحد شما توسط نماینده حذف گردید.").append("\n").append("لطفا پس از انتخاب واحد مجدد نماینده،جهت تایید به سامانه مراجعه فرمایید.")
                    .append("\n").append("https://unit.betaja.ir");
            smsService.sendMassages(mMember.getNationalCode(), mMember.getMobileNumber(), builder5.toString());
        });

        member.get().setFinalityChoice(false);
        member.get().setConfirmSelection(false);
        member.get().setConfirmChoiceDate(null);
        member.get().setIdCode(null);
        member.get().setConfirmCode(null);
        memberRepository.save(member.get());

        StringBuilder builder2 = new StringBuilder();
        builder2.append("*سامانه سرا بنیاد تعاون آجا*").append("\n").append("انتخاب واحد شما حذف گردید.").append("\n")
                .append(" نماینده گرامی لطفا برای انجام فرآیند انتخاب واحد جدید به سامانه مراجعه فرمایید. ")
                .append("\n").append("https://unit.betaja.ir");
        smsService.sendMassages(member.get().getNationalCode(), member.get().getMobileNumber(), builder2.toString());

        return true;
    }*/





    @Value("${system-step}")

    private String systemStep;

    public String getSystemStep() {
        return systemStep;
    }

    public void setSystemStep(String systemStep) {
        this.systemStep = systemStep;
    }




}
