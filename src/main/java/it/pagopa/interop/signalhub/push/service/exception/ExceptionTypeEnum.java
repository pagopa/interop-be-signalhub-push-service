package it.pagopa.interop.signalhub.push.service.exception;

import lombok.Getter;

@Getter
public enum ExceptionTypeEnum {

    CORRESPONDENCE_NOT_FOUND("CORRESPONDENCE_NOT_FOUND", "Non risulta corrispondenza tra l'erogatore e l'id del servizio: "),
    JWT_NOT_VALID("JWT_NOT_VALID", "Il voucher passato non è valido"),
    JWT_UNAUTHORIZED("JWT_UNAUTHORIZED", "Il voucher passato non è autorizzato a procedere"),
    UNAUTHORIZED("NOT_AUTHORIZED", "User not authorized"),
    JWT_TYPE_INCORRECT("JWT_TYPE_INCORRECT", "Il voucher passato non è del tipo atteso"),
    JWT_EMPTY("JWT_EMPTY", "Il voucher passato è vuoto"),
    JWT_NOT_PRESENT("JWT_NOT_PRESENT", "Il voucher non è stato passato"),
    JWT_PARSER_ERROR("JWT_PARSER_ERROR", "Non è stato possibile decodificare il voucher."),
    DETAIL_AGREEMENT_ERROR("DETAIL_AGREEMENT_ERROR", "Si è verificato un errore durante il recupero del dettaglio del purpose"),
    AGREEMENT_NOT_VALID("AGREEMENT_NOT_VALID", "Non si dispone delle autorizzazioni necessarie per utilizzare il sistema"),
    GENERIC_ERROR("GENERIC_ERROR", "Si è verificato un errore interno"),
    SIGNALID_ALREADY_EXISTS("SIGNALID_ALREADY_EXISTS", "Esiste già un signalId per il determinato servizio"),
    ESERVICE_STATUS_IS_NOT_PUBLISHED("ESERVICE_STATUS_IS_NOT_PUBLISHED", "Lo stato del servizio è diverso da PUBLISHED. EServiceId: "),
    ESERVICE_NOT_FOUND("ESERVICE_NOT_FOUND", "L'eservice non è presente a sistema - eserviceId: ");

    private final String title;
    private final String message;


    ExceptionTypeEnum(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
