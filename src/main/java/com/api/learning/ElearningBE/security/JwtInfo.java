package com.api.learning.ElearningBE.security;

import com.api.learning.ElearningBE.utils.ZipUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class JwtInfo {
    private static final String DELIM = "\\|";
    private static final String EMPTY_STRING = "<>";
    private Long accountId;
    private String email;
    private Integer kind;

    public JwtInfo decode(String input){
        JwtInfo result = null;
        try {
            String[] items = ZipUtils.unzipString(input).split(DELIM,3);
            if (items.length >= 3){
                result = new JwtInfo();
                result.setAccountId(parserLong(items[0]));
                result.setEmail(checkString(items[1]));
                result.setKind(parserInt(items[2]));
            }
        }catch (Exception e){
            log.error("Occurred an error when decoding: " +e.getMessage());
        }
        return result;
    }

    private static Long parserLong(String input){
        try{
            long out = Long.parseLong(input);
            if(out > 0){
                return  out;
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }

    private static Integer parserInt(String input){
        try{
            int out = Integer.parseInt(input);
            if(out > 0){
                return  out;
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }
    private static String checkString(String input){
        if(!input.equals(EMPTY_STRING)){
            return  input;
        }
        return  null;
    }
}
