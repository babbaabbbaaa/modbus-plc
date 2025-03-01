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

public record PLCSearchCriteria(
        short productTypeId,
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime from,
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime end,
        String barcode,
        String barcodeData,
        Boolean qualified,
        String autoInspectResult,
        String manualReinspectResult,
        String reinspectBy,
        int page,
        int size
) {


    @JsonIgnore
    @ApiParam(hidden = true)
    public PageRequest createPageRequest() {
        return PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "logTime"));
    }
}
