package com.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class PLCSearchCriteria {

    private short productTypeId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate from;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate end;
    private String barcode;
    private String barcodeData;
    private int page;
    private int size;

    @JsonIgnore
    @ApiParam(hidden = true)
    public PageRequest createPageRequest() {
        return PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "logTime"));
    }
}
