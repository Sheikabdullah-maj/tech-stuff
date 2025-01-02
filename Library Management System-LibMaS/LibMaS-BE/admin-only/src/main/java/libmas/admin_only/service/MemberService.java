package libmas.admin_only.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import libmas.admin_only.dto.GetAllMembersRequestDTO;
import libmas.admin_only.dto.MemberDetailsDTO;
import libmas.admin_only.entity.MemberDetails;
import libmas.admin_only.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public boolean saveMember(MemberDetailsDTO memberDetailsDTO){
        MemberDetails memberDetails = objectMapper.convertValue(memberDetailsDTO, MemberDetails.class);
        memberDetails = memberRepository.save(memberDetails);
        log.info("Member Details saved for book id:{}",memberDetails.getId());
        return memberDetails.getId()!=null;
    }

    public Page<MemberDetailsDTO> getMembers(GetAllMembersRequestDTO requestDTO, int pageNumber, int pageSize){
        Page<Map<Object, Object>> result = memberRepository.getMemberDetailsByPagination(requestDTO.getId(), requestDTO.getName(), requestDTO.getMobileNumber(),
                PageRequest.of(pageNumber, pageSize));
        List<Map<Object, Object>> rows = result.getContent();
        List<MemberDetailsDTO> memberDetailsDTOS = new ArrayList<>();
        for (Map<Object, Object> row:
                rows) {
            memberDetailsDTOS.add(objectMapper.convertValue(row, MemberDetailsDTO.class));
        }
        return new PageImpl(memberDetailsDTOS,PageRequest.of(pageNumber, pageSize), result.getTotalElements());
    }

    public MemberDetailsDTO getMemberDetail(Long id){
        Optional<MemberDetails> memberDetailsContainer =  memberRepository.findById(id);
        MemberDetailsDTO dto = MemberDetailsDTO.builder().build();
        if(memberDetailsContainer.isPresent()){
            dto = objectMapper.convertValue(memberDetailsContainer.get(),MemberDetailsDTO.class);
        }
        return dto;
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
        log.info("Member record deleted !!");
    }
}
