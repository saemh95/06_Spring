// 1. add SockJS lib  -> common.html

// 2. SockJS create obj
const testSock = new SockJS("/testSock");

// 3. testSock -> send message
const sendMessageFn = (name, str) => {
    // use JSON to transfer data as Text
    const obj = {
        "name" : name,
        "str" : str
    };
    testSock.send(JSON.stringify(obj));
}

// 4. websocket -> message

testSock.addEventListener("message", e => {

    // e.data : from server message (JSON)
    const msg = JSON.parse(e.data); // JSON -> JS object
    console.log(`${msg.name} : ${msg.str}`);

});