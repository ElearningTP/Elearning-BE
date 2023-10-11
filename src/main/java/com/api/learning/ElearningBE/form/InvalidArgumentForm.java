package com.api.learning.ElearningBE.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvalidArgumentForm {
    private String field;
    private String message;
}
