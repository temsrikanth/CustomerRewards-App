package com.rewards.util;

import java.time.LocalDate;

public class DateRange {
    private LocalDate start;
    private LocalDate end;

    public DateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() { 
    	return start; 
    	}
    public LocalDate getEnd() { 
    	return end; 
    	}

    public static DateRange lastNMonthsInclusive(int months) {
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusMonths(months);
        return new DateRange(start, end);
    }
}
