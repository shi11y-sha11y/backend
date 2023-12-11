package com.shillyshally.internalapi.application.dto;

public record DefaultResultResponse(
        String message
) {
    public static DefaultResultResponse ofSuccess() {
        return new DefaultResultResponse("성공적으로 요청을 수행했습니다.");
    }

    public static DefaultResultResponse ofFail() {
        return new DefaultResultResponse("요청을 처리하는데 실패했습니다.");
    }

    public static DefaultResultResponse ofSuccess(String message){
        return new DefaultResultResponse(message);
    }

    public static DefaultResultResponse ofFail(String message) {
        return new DefaultResultResponse(message);
    }
}
