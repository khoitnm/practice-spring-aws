package org.tnmk.practicespringaws.hotelview.model.converter;



import org.tnmk.common.jpa.columnconverter.json.JsonConverter;
import org.tnmk.practicespringaws.hotelview.model.MultiLanguageMetadata;

import javax.persistence.Converter;
import java.util.Map;

@Converter
public class MultiLanguageMetadataMapConverter extends JsonConverter<Map<String, MultiLanguageMetadata>> {

}
