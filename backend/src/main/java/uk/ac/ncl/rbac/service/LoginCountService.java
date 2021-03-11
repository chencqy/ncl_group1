package uk.ac.ncl.rbac.service;

import javax.servlet.http.HttpServletRequest;

public interface LoginCountService {

    void addLoginCount(HttpServletRequest request, int count);

    void cleanLoginCount(HttpServletRequest request);

    void judgeLoginCount(HttpServletRequest request);
}
