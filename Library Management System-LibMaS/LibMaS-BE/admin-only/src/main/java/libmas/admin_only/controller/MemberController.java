package libmas.admin_only.controller;

import libmas.admin_only.dto.CUDResponseDTO;
import libmas.admin_only.dto.GetAllMembersRequestDTO;
import libmas.admin_only.dto.MemberDetailsDTO;
import libmas.admin_only.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member/")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/getAllMembers")
    public Page<MemberDetailsDTO> getAllMembers(@RequestBody GetAllMembersRequestDTO requestDTO,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        return memberService.getMembers(requestDTO, page, size);
    }

    @RequestMapping("/saveMember")
    public CUDResponseDTO saveMember(@RequestBody MemberDetailsDTO memberDetailsDTO) {
        boolean isSaved = memberService.saveMember(memberDetailsDTO);
        return CUDResponseDTO.builder().processed(isSaved).build();
    }

    @GetMapping("/getMemberDetail")
    public MemberDetailsDTO getMemberById(@RequestParam Long id){
        return memberService.getMemberDetail(id);
    }

    @DeleteMapping("/deleteMember/{memberId}")
    public void deleteMember(@PathVariable("memberId") Long memberId){
        memberService.deleteMember(memberId);
    }
}
