package com.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class PLCSearchCriteria {

    private short productTypeId = 0;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime from;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;
    private String barcode;
    private String barcodeData;
    private Boolean qualified;
    private String autoInspectResult;
    private String manualReinspectResult;
    private String reinspectBy;
    private int page = 0;
    private int size = 10;

    @JsonIgnore
    @ApiParam(hidden = true)
    public PageRequest createPageRequest() {
        return PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "logTime"));
    }
}
