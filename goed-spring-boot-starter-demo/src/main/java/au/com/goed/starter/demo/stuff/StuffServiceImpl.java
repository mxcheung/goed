package au.com.goed.starter.demo.stuff;

import org.springframework.stereotype.Service;

@Service
public class StuffServiceImpl implements StuffService {

    @Override
    public String mimic(String str) {
        
        return str.toUpperCase();
    }

}
