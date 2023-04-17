// data/video.json 파일을 로딩하여 메인페이지 구성
window.onload = function () {
    let area = document.querySelector(".left");
    let area2 = document.querySelector(".container-top");
    
    fetch("./data/video.json")
        .then((response) => response.json())
        .then((videos) => {
            let index = 0;
            for (video of videos) {
                index++;
                if (video.part === "전신") {
                    let cardBody = area.querySelector(".card-body");
                    if (area === document.querySelector(".left")) {
                        cardBody.setAttribute("onclick", "movePage(1)");
                    } else {
                        cardBody.setAttribute("onclick", "movePage(2)")
                    }
                    let iframe = area.querySelector("iframe");
                    iframe.src = video.url;
                    let title = area.querySelector(".card-title");
                    title.innerHTML = video.title;
                    let text = area.querySelector(".card-text");
                    text.innerHTML = video.part;
                    area = document.querySelector(".right");
                }

                if (index === 1) {
                    let order = area2.querySelector(".first");
                    let iframe2 = order.querySelector("iframe");
                    iframe2.src = video.url;
                    let title2 = order.querySelector(".card-title");
                    title2.innerHTML = video.title;
                    let text2 = order.querySelector(".card-text");
                    text2.innerHTML = video.part;
                } else if (index === 2) {
                    let order = area2.querySelector(".second");
                    let iframe2 = order.querySelector("iframe");
                    iframe2.src = video.url;
                    let title2 = order.querySelector(".card-title");
                    title2.innerHTML = video.title;
                    let text2 = order.querySelector(".card-text");
                    text2.innerHTML = video.part;
                } else if (index === 3) {
                    let order = area2.querySelector(".third");
                    let iframe2 = order.querySelector("iframe");
                    iframe2.src = video.url;
                    let title2 = order.querySelector(".card-title");
                    title2.innerHTML = video.title;
                    let text2 = order.querySelector(".card-text");
                    text2.innerHTML = video.part;
                    area2 = document.querySelector(".container-bottom");
                } else if (index === 4) {
                    let order = area2.querySelector(".first");
                    let iframe2 = order.querySelector("iframe");
                    iframe2.src = video.url;
                    let title2 = order.querySelector(".card-title");
                    title2.innerHTML = video.title;
                    let text2 = order.querySelector(".card-text");
                    text2.innerHTML = video.part;
                    area2 = document.querySelector(".container-bottom");
                } else if (index === 5) {
                    let order = area2.querySelector(".second");
                    let iframe2 = order.querySelector("iframe");
                    iframe2.src = video.url;
                    let title2 = order.querySelector(".card-title");
                    title2.innerHTML = video.title;
                    let text2 = order.querySelector(".card-text");
                    text2.innerHTML = video.part;
                    area2 = document.querySelector(".container-bottom");
                } else if (index === 6) {
                    let order = area2.querySelector(".third");
                    let iframe2 = order.querySelector("iframe");
                    iframe2.src = video.url;
                    let title2 = order.querySelector(".card-title");
                    title2.innerHTML = video.title;
                    let text2 = order.querySelector(".card-text");
                    text2.innerHTML = video.part;
                    area2 = document.querySelector(".container-bottom");
                }
            }
        })

    const btn1 = document.querySelector("#part1");
    btn1.addEventListener("click", () => {
        let area = document.querySelector(".left");
        fetch("./data/video.json")
            .then((response) => response.json())
            .then((videos) => {
                for (video of videos) {
                    if (video.part === "전신") {
                        let cardBody = area.querySelector(".card-body");
                        if (area === document.querySelector(".left")) {
                            cardBody.setAttribute("onclick", "movePage(1)");
                        } else {
                            cardBody.setAttribute("onclick", "movePage(2)")
                        }
                        let iframe = area.querySelector("iframe");
                        iframe.src = video.url;
                        let title = area.querySelector(".card-title");
                        title.innerHTML = video.title;
                        let text = area.querySelector(".card-text");
                        text.innerHTML = video.part;
                        area = document.querySelector(".right");
                    }
                }
            })
    })

    const btn2 = document.querySelector("#part2");
    btn2.addEventListener("click", () => {
        let area = document.querySelector(".left");
        fetch("./data/video.json")
            .then((response) => response.json())
            .then((videos) => {
                for (video of videos) {
                    if (video.part === "상체") {
                        let cardBody = area.querySelector(".card-body");
                        if (area === document.querySelector(".left")) {
                            cardBody.setAttribute("onclick", "movePage(3)");
                        } else {
                            cardBody.setAttribute("onclick", "movePage(4)")
                        }
                        let iframe = area.querySelector("iframe");
                        iframe.src = video.url;
                        let title = area.querySelector(".card-title");
                        title.innerHTML = video.title;
                        let text = area.querySelector(".card-text");
                        text.innerHTML = video.part;
                        area = document.querySelector(".right");
                    }
                }
            })
    })

    const btn3 = document.querySelector("#part3");
    btn3.addEventListener("click", () => {
        let area = document.querySelector(".left");
        fetch("./data/video.json")
            .then((response) => response.json())
            .then((videos) => {
                for (video of videos) {
                    if (video.part === "하체") {
                        let cardBody = area.querySelector(".card-body");
                        if (area === document.querySelector(".left")) {
                            cardBody.setAttribute("onclick", "movePage(5)");
                        } else {
                            cardBody.setAttribute("onclick", "movePage(6)")
                        }
                        let iframe = area.querySelector("iframe");
                        iframe.src = video.url;
                        let title = area.querySelector(".card-title");
                        title.innerHTML = video.title;
                        let text = area.querySelector(".card-text");
                        text.innerHTML = video.part;
                        area = document.querySelector(".right");
                    }
                }
            })
    })

    const btn4 = document.querySelector("#part4");
    btn4.addEventListener("click", () => {
        let area = document.querySelector(".left");
        fetch("./data/video.json")
            .then((response) => response.json())
            .then((videos) => {
                for (video of videos) {
                    if (video.part === "복부") {
                        let cardBody = area.querySelector(".card-body");
                        if (area === document.querySelector(".left")) {
                            cardBody.setAttribute("onclick", "movePage(7)");
                        } else {
                            cardBody.setAttribute("onclick", "movePage(8)")
                        }
                        let iframe = area.querySelector("iframe");
                        iframe.src = video.url;
                        let title = area.querySelector(".card-title");
                        title.innerHTML = video.title;
                        let text = area.querySelector(".card-text");
                        text.innerHTML = video.part;
                        area = document.querySelector(".right");
                    }
                }
            })
    })
}

// 메인 페이지에서 리뷰 페이지로 전환시, 사용자 선택 정보를 제공하는 함수 
function movePage(n) {
    localStorage.setItem("key", n);
    if (n === 1) {
        localStorage.setItem("url", "https://www.youtube.com/embed/gMaB-fG4u4g");
    } else if (n === 2) {
        localStorage.setItem("url", "https://www.youtube.com/embed/swRNeYw1JkY");
    } else if (n === 3) {
        localStorage.setItem("url", "https://www.youtube.com/embed/54tTYO-vU2E");
    } else if (n === 4) {
        localStorage.setItem("url", "https://www.youtube.com/embed/QqqZH3j_vH0");
    } else if (n === 5) {
        localStorage.setItem("url", "https://www.youtube.com/embed/tzN6ypk6Sps");
    } else if (n === 6) {
        localStorage.setItem("url", "https://www.youtube.com/embed/u5OgcZdNbMo");
    } else if (n === 7) {
        localStorage.setItem("url", "https://www.youtube.com/embed/PjGcOP-TQPE");
    } else if (n === 8) {
        localStorage.setItem("url", "https://www.youtube.com/embed/7TLk7pscICk");
    }

    location.replace("review.html");
}