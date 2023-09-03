package telran.spring.college.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {

	List<String> array;
	String successMessage;
	String errorMessage;
}
