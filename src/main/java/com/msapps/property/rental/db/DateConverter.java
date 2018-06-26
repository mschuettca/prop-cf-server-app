package com.msapps.property.rental.db;

import java.sql.Date;

import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<java.util.Date, java.sql.Date>{
   // private static final Logger LOGGER = LoggerFactory.getLogger(DateConverter.class);
  //  private final String dateFormat;
  //  private final SimpleDateFormat formatter;
  //  public DateConverter(String dateFormatPattern) {
 //       this.dateFormat = dateFormatPattern;
 //       this.formatter = new SimpleDateFormat(dateFormatPattern);
 //   }

    @Override
    public Date convert(java.util.Date source) {
        Date date = new Date(source.getTime());
        return date;
    }
}
