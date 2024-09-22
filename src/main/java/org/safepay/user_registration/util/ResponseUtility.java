package org.safepay.user_registration.util;

import lombok.Getter;
import lombok.Setter;

//Just a simple class to carry the user verification status and response from AccountVerificationService to AccountVerificationController.
//Can be used wherever as required.
@Getter
@Setter
public class ResponseUtility {

    Boolean result;

    String message;

}
