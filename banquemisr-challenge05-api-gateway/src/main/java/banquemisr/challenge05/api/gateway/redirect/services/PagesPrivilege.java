package banquemisr.challenge05.api.gateway.redirect.services;

import lombok.Getter;

@Getter
public enum PagesPrivilege {

    NO_AUTH_PAGES(BaseServicesUrls.TASK_MANAGEMENT_SERVICE_BASE_URL + "/actuator/**"),

    USER_AUTH_PAGES(
            BaseServicesUrls.TASK_MANAGEMENT_SERVICE_BASE_URL + "/get-tasks," +
                    BaseServicesUrls.TASK_MANAGEMENT_SERVICE_BASE_URL + "/create-task," +
                    BaseServicesUrls.TASK_MANAGEMENT_SERVICE_BASE_URL + "/update-task," +
                    BaseServicesUrls.TASK_MANAGEMENT_SERVICE_BASE_URL + "/delete-task/**"),

    ADMIN_AUTH_PAGES(BaseServicesUrls.TASK_MANAGEMENT_SERVICE_BASE_URL + "/get-tasks-history");

    private final String value;

    PagesPrivilege(String pagesUrls) {
        this.value = pagesUrls;
    }
}
