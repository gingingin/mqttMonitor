package com.yzwy.application;

import com.yzwy.domain.repository.Msg_templetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by gin on 2017/6/1.
 */

public interface MessageService {

     void calculateTemplet(Integer id);

     void generateTempletJson() throws IOException;

      void caclulateAllTemplet();
}
