package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Business;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BusinessServiceImplTest {
    private static final long ID =1;
    private static final String BUSINESS_NAME = "Business";
    private static final long USER_ID = 1;
    private static final String TELEPHONE = "123456789";
    private static final String EMAIL = "mail@mail.com";
    private static final String LOCATION = null;

    @InjectMocks
    private BusinessServiceImpl businessService;
    @Mock
    private BusinessDao businessDao;


}
