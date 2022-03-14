package com.moliang.labdocsys.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zhang qing
 * @date 2022/3/14 10:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperimentForm {

    private String text;

    private String name;

    private Date endTime;

}
