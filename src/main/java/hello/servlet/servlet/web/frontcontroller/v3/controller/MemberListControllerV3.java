package hello.servlet.servlet.web.frontcontroller.v3.controller;

import hello.servlet.servlet.domain.Member;
import hello.servlet.servlet.domain.MemberRepository;
import hello.servlet.servlet.web.frontcontroller.ModelView;
import hello.servlet.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.List;
import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        List<Member> members = memberRepository.findAll();
        ModelView modelView = new ModelView("members");
        modelView.getModel().put("members", members);
        return modelView;
    }
}
