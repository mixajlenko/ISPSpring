package com.mixajlenko.ispspring.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TariffInfoForClientDTO {

    private String name;

    private boolean tariffStatus;

    private Date nextBill;

    private  Date subDate;

}
