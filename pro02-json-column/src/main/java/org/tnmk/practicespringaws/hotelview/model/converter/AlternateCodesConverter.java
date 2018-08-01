package org.tnmk.practicespringaws.hotelview.model.converter;



import org.tnmk.common.jpa.columnconverter.json.JsonConverter;
import org.tnmk.practicespringaws.hotelview.model.AlternateCode;

import javax.persistence.Converter;
import java.util.List;

@Converter
public class AlternateCodesConverter extends JsonConverter<List<AlternateCode>> {

}
