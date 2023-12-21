package com.api.learning.ElearningBE.services.account;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.account.AccountAdminDto;
import com.api.learning.ElearningBE.dto.account.AccountDto;
import com.api.learning.ElearningBE.dto.account.StudentScheduleDto;
import com.api.learning.ElearningBE.dto.assignment.AssignmentDto;
import com.api.learning.ElearningBE.dto.assignment.AssignmentScheduleDto;
import com.api.learning.ElearningBE.dto.quiz.QuizDto;
import com.api.learning.ElearningBE.dto.quiz.QuizScheduleDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.account.CreateAccountForm;
import com.api.learning.ElearningBE.mapper.*;
import com.api.learning.ElearningBE.repositories.*;
import com.api.learning.ElearningBE.security.JwtUtils;
import com.api.learning.ElearningBE.security.impl.UserService;
import com.api.learning.ElearningBE.storage.criteria.AccountCriteria;
import com.api.learning.ElearningBE.storage.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private NationRepository nationRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private AssignmentMapper assignmentMapper;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuizMapper quizMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private AssignmentSubmissionRepository assignmentSubmissionRepository;
    @Autowired
    private AssignmentSubmissionMapper assignmentSubmissionMapper;
    @Autowired
    private QuizSubmissionRepository quizSubmissionRepository;
    @Autowired
    private QuizSubmissionMapper quizSubmissionMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    private String extractToken(String token){
        if (!StringUtils.isEmpty(token) && token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;
    }

    @Override
    public ApiMessageDto<ResponseListDto<List<AccountAdminDto>>> list(AccountCriteria accountCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<AccountAdminDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<AccountAdminDto>> responseListDto = new ResponseListDto<>();
        Page<Account> accounts = accountRepository.findAll(accountCriteria.getSpecification(),pageable);
        List<AccountAdminDto> accountDtoList = accountMapper.fromEntityToAccountAdminDtoList(accounts.getContent());

        responseListDto.setContent(accountDtoList);
        responseListDto.setPageIndex(accounts.getNumber());
        responseListDto.setPageSize(accounts.getSize());
        responseListDto.setTotalPages(accounts.getTotalPages());
        responseListDto.setTotalElements(accounts.getTotalElements());

        apiMessageDto.setData(responseListDto);
        return apiMessageDto;
    }


    /// Check query again
    @Override
    public ApiMessageDto<ResponseListDto<List<AccountDto>>> autoComplete(AccountCriteria accountCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<AccountDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<AccountDto>> responseListDto = new ResponseListDto<>();
        Page<Account> accounts = accountRepository.findAllByPredicate(accountCriteria,pageable);
        List<AccountDto> accountDtoList = accountMapper.fromEntityToAccountDtoAutoCompleteList(accounts.getContent());

        responseListDto.setContent(accountDtoList);
        responseListDto.setPageIndex(accounts.getNumber());
        responseListDto.setPageSize(accounts.getSize());
        responseListDto.setTotalPages(accounts.getTotalPages());
        responseListDto.setTotalElements(accounts.getTotalElements());

        apiMessageDto.setData(responseListDto);
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<ResponseListDto<List<AccountDto>>> getAllStudentByCourse(Long courseId, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<AccountDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<AccountDto>> responseListDto = new ResponseListDto<>();
        Page<Account> accounts = accountRepository.findAllStudentByCourseId(courseId,pageable);
        List<AccountDto> accountDtoList = accountMapper.fromEntityToAccountDtForTheSameOfCourseList(accounts.getContent());

        responseListDto.setContent(accountDtoList);
        responseListDto.setPageIndex(accounts.getNumber());
        responseListDto.setPageSize(accounts.getSize());
        responseListDto.setTotalPages(accounts.getTotalPages());
        responseListDto.setTotalElements(accounts.getTotalElements());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve all students successfully");
        return apiMessageDto;
    }


    @Override
    public ApiMessageDto<ResponseListDto<List<AccountDto>>> memberTheSameCourse(Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<AccountDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<AccountDto>> responseListDto = new ResponseListDto<>();
        Long studentId = userService.getAccountId();
        Page<Account> accounts = accountRepository.findAllMemberTheSameCourse(studentId,pageable);
        List<AccountDto> accountDtoList = accountMapper.fromEntityToAccountDtForTheSameOfCourseList(accounts.getContent());

        responseListDto.setContent(accountDtoList);
        responseListDto.setTotalElements(accounts.getTotalElements());
        responseListDto.setTotalPages(accounts.getTotalPages());
        responseListDto.setPageSize(accounts.getSize());
        responseListDto.setPageIndex(accounts.getNumber());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve member list successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<AccountDto> retrieveMe(String token) {
        ApiMessageDto<AccountDto> apiMessageDto = new ApiMessageDto<>();
        String jwt = extractToken(token);
        String email = jwtUtils.getEmailFromToken(jwt);
        Account account = accountRepository.findByEmail(email);
        if (account == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(String.format("Account with email %s not found", email));
            return apiMessageDto;
        }
        AccountDto accountDto = accountMapper.fromEntityToAccountDtoForMe(account);

        apiMessageDto.setData(accountDto);
        apiMessageDto.setMessage("Retrieve info successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<StudentScheduleDto> mySchedule() {
        ApiMessageDto<StudentScheduleDto> apiMessageDto = new ApiMessageDto<>();
        Long studentId = userService.getAccountId();
        // Assignment
        List<Object[]> assignments = assignmentRepository.findAllAssignmentByStudentId(studentId);
        List<Long> assignmentIds = assignments.stream()
                .map(objects -> (Assignment) objects[0])
                .map(Assignment::getId)
                .collect(Collectors.toList());
        List<Long> courseIdsAssignment = assignments.stream()
                .map(objects -> (Course) objects[1])
                .map(Course::getId)
                .collect(Collectors.toList());
        Map<Long,List<AssignmentSubmission>> assignmentSubmissionsMap =
                assignmentSubmissionRepository.findAllByAssignmentIdInAndStudentIdAndCourseIdIn(assignmentIds,studentId,courseIdsAssignment).parallelStream()
                .collect(Collectors.groupingByConcurrent(assignmentSubmission -> assignmentSubmission.getAssignment().getId()));
        List<AssignmentScheduleDto> assignmentSchedules = new ArrayList<>();
        for (Object[] o: assignments){
            AssignmentScheduleDto assignmentSchedule = new AssignmentScheduleDto();
            assignmentSchedule.setAssignmentInfo(assignmentMapper.fromEntityToAssignmentDtoForMySchedule((Assignment) o[0]));
            assignmentSchedule.setCourseInfo(courseMapper.fromEntityToCourseDtoForMySchedule((Course) o[1]));
            assignmentSchedules.add(assignmentSchedule);
        }
        List<AssignmentDto> assignmentDtoList = assignmentSchedules.parallelStream()
                .map(AssignmentScheduleDto::getAssignmentInfo)
                .collect(Collectors.toList());
        assignmentDtoList.replaceAll(assignmentDto -> {
            List<AssignmentSubmission> submissions = assignmentSubmissionsMap.getOrDefault(assignmentDto.getId(), Collections.emptyList());
            assignmentDto.setAssignmentSubmissionInfo(assignmentSubmissionMapper.fromEntityToAssignmentSubmissionDtoForMyScheduleList(submissions));
            return assignmentDto;
        });

        // Quiz
        List<Object[]> quizzes = quizRepository.findAllQuizByStudentId(studentId);
        List<Long> quizIds = quizzes.stream()
                .map(objects -> (Quiz) objects[0])
                .map(Quiz::getId)
                .collect(Collectors.toList());
        List<Long> courseIdsQuiz = quizzes.stream()
                .map(objects -> (Course) objects[1])
                .map(Course::getId)
                .collect(Collectors.toList());
        Map<Long,List<QuizSubmission>> quizSubmissionsMap = quizSubmissionRepository.findAllByStudentIdAndCourseIdInAndQuizIdIn(studentId,courseIdsQuiz,quizIds).parallelStream()
                .collect(Collectors.groupingByConcurrent(quizSubmission -> quizSubmission.getQuiz().getId()));
        List<QuizScheduleDto> quizSchedules = new ArrayList<>();
        for (Object[] o: quizzes){
            QuizScheduleDto quizSchedule = new QuizScheduleDto();
            quizSchedule.setQuizInfo(quizMapper.fromEntityToQuizDtoForMySchedule((Quiz) o[0]));
            quizSchedule.setCourseInfo(courseMapper.fromEntityToCourseDtoForMySchedule((Course) o[1]));
            quizSchedules.add(quizSchedule);
        }
        List<QuizDto> quizDtoList = quizSchedules.parallelStream()
                .map(QuizScheduleDto::getQuizInfo)
                .collect(Collectors.toList());
        quizDtoList.replaceAll(quizDto -> {
            List<QuizSubmission> quizSubmissions = quizSubmissionsMap.getOrDefault(quizDto.getId(), Collections.emptyList());
            quizDto.setQuizSubmissionInfo(quizSubmissionMapper.fromEntityToQuizSubmissionDtoList(quizSubmissions));
            return quizDto;
        });

        StudentScheduleDto studentScheduleDto = new StudentScheduleDto();
        studentScheduleDto.setAssignmentsInfo(assignmentSchedules);
        studentScheduleDto.setQuizzesInfo(quizSchedules);

        apiMessageDto.setData(studentScheduleDto);
        apiMessageDto.setMessage("Retrieve schedule successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> create(CreateAccountForm createAccountForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Boolean emailExisted = accountRepository.existsAccountByEmail(createAccountForm.getEmail().trim());
        if (emailExisted){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(String.format("Email %s is existed", createAccountForm.getEmail()));
            return apiMessageDto;
        }
        Nation nation = nationRepository.findById(createAccountForm.getNationId())
                .orElseThrow(() -> new NotFoundException(String.format("Nation with id %s not found", createAccountForm.getNationId())));
        Role role = roleRepository.findById(createAccountForm.getRoleId())
                .orElseThrow(() -> new NotFoundException(String.format("Role with id %s not found", createAccountForm.getRoleId())));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(createAccountForm.getPassword());

        Account account = accountMapper.fromCreateAccountFormToEntity(createAccountForm);
        account.setNation(nation);
        account.setRole(role);
        account.setPassword(hash);
        accountRepository.save(account);

        apiMessageDto.setMessage("Create account successfully.");
        return apiMessageDto;

    }

}
