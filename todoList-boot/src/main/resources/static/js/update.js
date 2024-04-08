const goBack = document.querySelector("#goBack");

goBack.addEventListener("click", () => {
    location.href="/todo/detail?todoNo=1";
});