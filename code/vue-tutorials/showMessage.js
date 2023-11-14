function simpleMessage(msg) {
    console.log(msg);
}

function complexMessage(msg) {
    console.log(new Date() + ": " + msg);
}

// 批量导出
export default {simpleMessage, complexMessage}