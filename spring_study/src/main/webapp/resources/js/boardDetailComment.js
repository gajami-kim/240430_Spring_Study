//순서 포스트(비동기 함수 작성, 이벤트 삽입), 출력

// console.log("js연결!");
// console.log(bnoVal);
// console.log(id)

//댓글객체생성
document.getElementById('cmtAddBtn').addEventListener('click',()=>{
    const cmtText = document.getElementById('cmtText').value;
    const cmtWriter = document.getElementById('cmtWriter').innerText;

    if(cmtText==null | cmtText=="") {
        alert("댓글을 입력해주세요");
        document.getElementById('cmtText').focus;
        return false;
    } else {
        let cmtData = {
            bno:bnoVal,
            writer:cmtWriter,
            content:cmtText
        }
        console.log(cmtData);
        postCommentToServer(cmtData).then(result=>{
            console.log(result);
            if(result=="1") {
                alert("댓글이 등록되었습니다.");
                document.getElementById('cmtText').value="";
                //화면에 뿌리기 작업
                //cmtData안에 bnoVal을 매개변수로
                spreadCommentList(bnoVal);
            }
        })
    }

})

//비동기통신 restful
//post : 데이터 객체를 controller로 보낼때(삽입)
//get : 조회(생략가능)
//put(patch) : 수정
//delete : 삭제
async function postCommentToServer(cmtData) {
    try {
        const url = "/comment/post";
        const config ={
            method:"post",
            headers:{
                "content-type":"application/json; charset=utf-8"
            },
            body:JSON.stringify(cmtData)
        };

        const resp = await fetch(url, config);
        const result = await resp.text(); //return=>isOk
        return result;
    } catch (error) {
        console.log(error);
    }
}


//댓글 뿌리기
function spreadCommentList(bno) {
    getCommentListFromServer(bno).then(result=>{
        console.log(result);
        const div = document.getElementById('accordionExample');
        if(result.length>0) {
            div.innerHTML = ''; //반복 전 기존 샘플 초기화
            //반복
            for(let i=0; i<result.length; i++) {
                let add = `<div class="accordion-item">`;
                add+=`<h2 class="accordion-header">`;
                add+=`<button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${i}" aria-expanded="true" aria-controls="collapse${i}">`;
                add+=`no.${result[i].cno} / ${result[i].writer} /  [${result[i].reg_date}]`;
                add+=`</button></h2>`;
                add+=`<div id="collapse${i}" class="accordion-collapse collapse show" data-bs-parent="#accordionExample">`;
                add+=`<div class="accordion-body">`;
                if(id==result[i].writer) {
                    add+=`<button type="button" data-cno="${result[i].cno}" class="btn btn-outline-info btn-sm cmtModBtn">수정</button>`;
                    add+=`<button type="button" data-cno="${result[i].cno}" class="btn btn-outline-danger btn-sm cmtDelBtn">삭제</button>`;
                    add+=`<input type="text" class="form-control cmtText" value="${result[i].content}">`;
                }
                if(id!=result[i].writer) {
                    add+=`<input type="text" class="form-control cmtText" value="${result[i].content}" readonly="readonly">`;
                }
                add+=`</div></div></div>`;
                div.innerHTML += add;
                /*
                <div class="accordion" id="accordionExample">
                  <div class="accordion-item">
                    <h2 class="accordion-header">
                      <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                        cno, writer, reg_date
                      </button>
                    </h2>
                    <div id="collapseOne" class="accordion-collapse collapse show" data-bs-parent="#accordionExample">
                      <div class="accordion-body">
                        <p>댓글내용표시</p>
                      </div>
                    </div>
                  </div>
                </div>
                <br>
                */
            }
        } else {
            div.innerHTML=`<div class="accordion-body"> Comment List Empty </div>`;
        }
    })
}

//출력할 값 가져오기
async function getCommentListFromServer(bno){
    try {
        //경로 /comment/308(bno)
        //get이 생략된 상태
        const resp = await fetch("/comment/"+bno);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}


//수정, 삭제 버튼 값 가져오기
document.addEventListener('click',(e)=>{
    console.log(e.target);
    if(e.target.classList.contains('cmtModBtn')) {
        let cnoVal = e.target.dataset.cno;

        //e.target 기준 가장 가까운 div 값 찾기(cmtText를 가져오기 위해)
        let div = e.target.closest('div');
        let cmtText = div.querySelector('.cmtText').value;
        let cmtData = {
            cno:cnoVal,
            content:cmtText
        };

        //update 비동기 함수 호출
        updateCommentToServer(cmtData).then(result=>{
            if(result=='1') {
                alert('댓글 수정 완료');
                spreadCommentList(bnoVal);
            }
        })
    }

    if(e.target.classList.contains('cmtDelBtn')) {
        let cnoVal = e.target.dataset.cno;

        //remove 호출
        removeCommentFromServer(cnoVal, bnoVal).then(result=>{
            if(result=="1") {
                alert('댓글 삭제 완료');
                spreadCommentList(bnoVal);
            }
        })
    }
})





//수정
async function updateCommentToServer(cmtData){
    try {
        const url = "/comment/modify";
        const config = {
            method:"put",
            headers:{
                "content-type":"application/json; charset=utf-8"
            },
            body:JSON.stringify(cmtData)
        };

        const resp = await fetch(url, config);
        const result = await resp.text(); //isOk
        return result;
    } catch (error) {
        console.log(error);
    }
}

//삭제
async function removeCommentFromServer(cno, bno) {
    try {
        //get 외의 method는 생략 불가능
        const url = "/comment/"+cno+"/"+bno;
        const config = {
            method:"delete"
        }
        
        const resp = await fetch(url,config);
        const result = await resp.text(); //isOk
        return result;
    } catch (error) {
        console.log(error);
    }
}