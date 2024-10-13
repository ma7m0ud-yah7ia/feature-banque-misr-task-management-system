package banquemisr.challenge05.task.management.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GeneralPageableDTO extends GeneralResponseDTO {
    private int page;
    private int totalPages;
    private int totalSize;

}
