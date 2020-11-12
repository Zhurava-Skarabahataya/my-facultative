package by.epamtc.facultative.service;

import by.epamtc.facultative.service.impl.AuthorizationServiceImpl;
import by.epamtc.facultative.service.impl.CourseCreatorServiceImpl;
import by.epamtc.facultative.service.impl.CourseInfoServiceImpl;
import by.epamtc.facultative.service.impl.DepartmentInfoServiceImpl;
import by.epamtc.facultative.service.impl.EmployeeStatusServiceImpl;
import by.epamtc.facultative.service.impl.FeedbackServiceImpl;
import by.epamtc.facultative.service.impl.FullNameServiceImpl;
import by.epamtc.facultative.service.impl.NewsServiceImpl;
import by.epamtc.facultative.service.impl.PasswordEncryptorImpl;
import by.epamtc.facultative.service.impl.RatingServiceImpl;
import by.epamtc.facultative.service.impl.RegistrationServiceImpl;
import by.epamtc.facultative.service.impl.StudentStatusServiceImpl;
import by.epamtc.facultative.service.impl.UpdateUserInfoServiceImpl;
import by.epamtc.facultative.service.impl.UserInfoServiceImpl;
import by.epamtc.facultative.service.impl.WelcomePageServiceImpl;

public class ServiceProvider {
	
	private static final ServiceProvider instance = new ServiceProvider();
	
	private final FullNameService fullNameService = FullNameServiceImpl.getInstance();
	private final AuthorizationService authorizationUserService = AuthorizationServiceImpl.getInstance();
	private final CourseInfoService courseInfoService = CourseInfoServiceImpl.getInstance();
	private final CourseCreatorService courseCreatorService = CourseCreatorServiceImpl.getInstance();
	private final DepartmentInfoService departmentInfoService = DepartmentInfoServiceImpl.getInstance();
	private final RatingService ratingService = RatingServiceImpl.getInstance();
	private final FeedbackService feedbackService = FeedbackServiceImpl.getInstance();
	private final EmployeeStatusService employeeStatusService = EmployeeStatusServiceImpl.getInstance();
	private final PasswordEncryptor passwordEncryptor = PasswordEncryptorImpl.getInstance();
	private final RegistrationService registrationService = RegistrationServiceImpl.getInstance();
	private final StudentStatusService studentStatusService = StudentStatusServiceImpl.getInstance();
	private final UserInfoService userInfoService = UserInfoServiceImpl.getInstance();
	private final UpdateUserInfoService updateUserInfoService = UpdateUserInfoServiceImpl.getInstance();
	private final WelcomePageService welcomePageService = WelcomePageServiceImpl.getInstance();
	private final NewsService newsService = NewsServiceImpl.getInstance();
	
	private ServiceProvider() {
		
	}
	
	public static ServiceProvider getInstance() {
		return instance;
	}

	public FullNameService getFullNameService() {
		return fullNameService;
	}

	public AuthorizationService getAuthorizationUserService() {
		return authorizationUserService;
	}

	public CourseInfoService getCourseInfoService() {
		return courseInfoService;
	}

	public CourseCreatorService getCourseCreatorService() {
		return courseCreatorService;
	}

	public DepartmentInfoService getDepartmentInfoService() {
		return departmentInfoService;
	}

	public RatingService getRatingService() {
		return ratingService;
	}

	public FeedbackService getFeedbackService() {
		return feedbackService;
	}

	public EmployeeStatusService getEmployeeStatusService() {
		return employeeStatusService;
	}

	public PasswordEncryptor getPasswordEncryptor() {
		return passwordEncryptor;
	}

	public RegistrationService getRegistrationService() {
		return registrationService;
	}

	public StudentStatusService getStudentStatusService() {
		return studentStatusService;
	}

	public UserInfoService getUserInfoService() {
		return userInfoService;
	}

	public UpdateUserInfoService getUpdateUserInfoService() {
		return updateUserInfoService;
	}

	public WelcomePageService getWelcomePageService() {
		return welcomePageService;
	}

	public NewsService getNewsService() {
		return newsService;
	}
	
	

	
	
	
}
