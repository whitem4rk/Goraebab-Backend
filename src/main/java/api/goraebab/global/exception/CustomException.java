package api.goraebab.global.exception;

import java.util.List;
import java.util.Map;
import lombok.Getter;

/**
 * Custom exception class for handling application-specific errors.
 *
 * <p>This exception extends {@link RuntimeException}, allowing it to be used for unchecked
 * exceptions. It includes additional fields for capturing detailed information about the error.
 *
 * <p>Provides multiple constructors for different use cases, including custom error messages,
 * nested exceptions, and additional metadata.
 *
 * @author whitem4rk
 * @version 1.0
 * @see RuntimeException
 * @see ErrorCode
 */
@Getter
public class CustomException extends RuntimeException {

  private final ErrorCode errorCode;
  private List<Map<String, Object>> failedContainers;
  private List<Map<String, Object>> succeededContainers;

  public CustomException(String message, ErrorCode errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public CustomException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public CustomException(ErrorCode errorCode, Throwable cause) {
    super(errorCode.getMessage(), cause);
    this.errorCode = errorCode;
  }

  public CustomException(
      ErrorCode errorCode,
      String message,
      List<Map<String, Object>> failedContainers,
      List<Map<String, Object>> succeededContainers) {
    super(message);
    this.errorCode = errorCode;
    this.failedContainers = failedContainers;
    this.succeededContainers = succeededContainers;
  }
}
