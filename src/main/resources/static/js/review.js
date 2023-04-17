// review.html 로드시, local storage로부터 영상 및 관련 정보를 가져온 후 리뷰 리스트 테이블에 표시
window.onload = function () {
    let key = localStorage.getItem("key");
    let reviews = localStorage.getItem(key);

    // 사용자 선택 영상의 url 정보 제공
    let iframe = document.querySelector("iframe");
    let url = localStorage.getItem("url");
    iframe.src = url;

    // 사용자 선택 영상의 리뷰 정보 제공
    let tbody = document.querySelector("tbody");
    if (reviews !== null) {
        reviews = JSON.parse(reviews);

        for (let i = 0; i < reviews.length; i++) {
            let reviewListHtml = document.createElement("tr");
            reviewListHtml.setAttribute("data-bs-toggle", "modal");
            reviewListHtml.setAttribute("data-bs-target", "#detailModal");
            reviewListHtml.innerHTML += `<td>${i + 1}</td> <td>${reviews[i]["title"]}</td>  <td>${reviews[i]["writer"]}</td> <td>${reviews[i]["visitNum"]}</td> <td>${reviews[i]["writeTime"]}</td>`;
            tbody.appendChild(reviewListHtml);
        }
    }

    // 리뷰 리스트 테이블의 행 클릭시, 리뷰 상세 화면에서 필요한 정보를 나타내는 함수 호출
    rowClicked();
};

// 리뷰 페이지에서 메인 페이지로 이동하는 함수
function moveMainPage() {
    location.replace("main.html");
}

// 리뷰작성 버튼 클릭 시, local storage에 작성 내용을 저장하는 함수 
const enroll = document.querySelector(".enroll");
enroll.addEventListener("click", function () {
    let key = localStorage.getItem("key");

    let title = document.querySelector(".reviewtitle").value;
    let content = document.querySelector(".reviewdetail").value;
    let writer = "SSAFY";
    let visitNum = 1;

    let date = new Date();
    let year = date.getFullYear().toString();
    let month = date.getMonth() + 1;
    month = month < 10 ? '0' + month.toString() : month.toString();
    let day = date.getDate();
    day = day < 10 ? '0' + day.toString() : day.toString();
    let hour = date.getHours();
    hour = hour < 10 ? '0' + hour.toString() : hour.toString();
    let minute = date.getMinutes();
    minute = minute < 10 ? '0' + minute.toString() : minute.toString();
    let second = date.getSeconds();
    second = second < 10 ? '0' + second.toString() : second.toString();
    let writeTime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;

    const review = {
        title: title,
        content: content,
        writer: writer,
        visitNum: visitNum,
        writeTime: writeTime
    };

    let localReviews = localStorage.getItem(key);

    if (localReviews === null) {
        localReviews = [review];
    } else {
        localReviews = JSON.parse(localReviews);
        localReviews.push(review);
    }

    const reviewsJson = JSON.stringify(localReviews);
    localStorage.setItem(key, reviewsJson);

    location.replace("review.html");
});

// 리뷰 리스트 테이블 행 클릭시, 리뷰 상세 화면에 필요한 정보를 나타내는 함수
function rowClicked() {
    let key = localStorage.getItem("key");
    let localReviews = localStorage.getItem(key);
    localReviews = JSON.parse(localReviews);

    let titleHTML = document.querySelector(".titleHTML ");
    let writerHTML = document.querySelector(".writerHTML");
    let dateHTML = document.querySelector(".dateHTML");
    let visitHTML = document.querySelector(".visitHTML");
    let contentHTML = document.querySelector(".contentHTML ");

    let table = document.querySelector("table");
    let rowList = table.rows;

    for (i = 1; i < rowList.length; i++) {
        let row = rowList[i];

        row.onclick = function () {
            return function () {
                let row_value = this.cells[0].innerHTML;
                row_value *= 1;
                localStorage.setItem("selectReview", row_value - 1);

                let title = localReviews[row_value - 1].title;
                titleHTML.innerHTML = title;
                let writer = localReviews[row_value - 1].writer;
                writerHTML.innerHTML = "작성자 : " + writer;
                let writeTime = localReviews[row_value - 1].writeTime;
                dateHTML.innerHTML = "작성일 : " + writeTime;
                let visitNum = localReviews[row_value - 1].visitNum;
                visitHTML.innerHTML = "조회수 : " + visitNum;
                let content = localReviews[row_value - 1].content;
                contentHTML.innerHTML = content;
            };
        }(row);
    }
}

// 삭제 버튼 클릭시, 리뷰를 삭제하는 함수
const reviewDeleteBtn = document.querySelector(".delete");
reviewDeleteBtn.addEventListener("click", function () {
    let key = localStorage.getItem("key");
    let localReviews = localStorage.getItem(key);
    localReviews = JSON.parse(localReviews);

    let selectReviewIdx = localStorage.getItem("selectReview");
    localReviews.splice(selectReviewIdx, 1);
    const reviewsJson = JSON.stringify(localReviews);
    localStorage.setItem(key, reviewsJson);

    location.replace("review.html");
});

// 수정 버튼 클릭시, 사용자 선택 리뷰의 정보를 리뷰 수정 화면으로 제공하는 함수
const reviewModifyBtn = document.querySelector(".modify");
reviewModifyBtn.addEventListener("click", function () {
    let key = localStorage.getItem("key");
    let localReviews = localStorage.getItem(key);
    localReviews = JSON.parse(localReviews);
    
    let selectReviewIdx = localStorage.getItem("selectReview");
    let reviewTitleHTML = document.querySelector("#modifyModalInput");
    reviewTitleHTML.value = localReviews[selectReviewIdx].title;
})

// 수정 완료 버튼 클릭시, local storage에 리뷰 수정 정보를 반영하는 함수
const confirmModifyBtn = document.querySelector(".confirmModify");
confirmModifyBtn.addEventListener("click", function () {
    let key = localStorage.getItem("key");
    let localReviews = localStorage.getItem(key);
    localReviews = JSON.parse(localReviews);
    
    let selectReviewIdx = localStorage.getItem("selectReview");
    localReviews[selectReviewIdx].title = document.querySelector("#modifyModalInput").value;
    localReviews[selectReviewIdx].content = document.querySelector("#modifyModalTextArea").value;
    let date = new Date();
    let year = date.getFullYear().toString();
    let month = date.getMonth() + 1;
    month = month < 10 ? '0' + month.toString() : month.toString();
    let day = date.getDate();
    day = day < 10 ? '0' + day.toString() : day.toString();
    let hour = date.getHours();
    hour = hour < 10 ? '0' + hour.toString() : hour.toString();
    let minute = date.getMinutes();
    minute = minute < 10 ? '0' + minute.toString() : minute.toString();
    let second = date.getSeconds();
    second = second < 10 ? '0' + second.toString() : second.toString();
    let writeTime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second + " (수정)";
    localReviews[selectReviewIdx].writeTime = writeTime;

    const reviewsJson = JSON.stringify(localReviews);
    localStorage.setItem(key, reviewsJson);

    location.replace("review.html");
})