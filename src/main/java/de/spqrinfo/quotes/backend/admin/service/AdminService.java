package de.spqrinfo.quotes.backend.admin.service;

import de.spqrinfo.quotes.backend.admin.dao.AdminDao;
import de.spqrinfo.quotes.backend.admin.domain.CustomUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@Service
public class AdminService {

    private static final Logger log = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private AdminDao adminDao;

    @Transactional
    public List<CustomUser> getUsers() {
        return this.adminDao.findAll();
    }

    @Transactional
    public void disableUser(final String userId) {
        this.adminDao.disableUser(userId);
    }

    @Transactional
    public void enableUser(final String userId) {
        this.adminDao.enableUser(userId);
    }
}
