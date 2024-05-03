console.log("board comment mod 연결");

document.getElementById('cmtAddBtn').addEventListener('click',()=>{
    const cmtText = document.getElementById('cmtText').value;
    const cmtWriter = document.getElementById('cmtWriter').innerText;
    console.log(cmtWriter);
    if(cmtText == null || cmtText=='') {
        alert("댓글을 입력해주세요");
        document.getElementById('cmtText').focus();
        return false;
    } else {
        let cmtData= {
            bno:bnoVal,
            writer:cmtWriter,
            content:cmtText
        }
        console.log(cmtData);
        postCommentToServer(cmtData).then(result=> {
            console.log(result);
            if(result=="1") {
                alert("댓글등록성공");
                document.getElementById('cmtText').value='';
                spreadCommentList(bnoVal);
            }
        })
    }

})

async function postCommentToServer(cmtData) {
    try {
        const url = "/comment/post";
        const config = {
            method:"post",
            headers:{
                "content-type":"application/json; charset=utf-8"
            },
            body:JSON.stringify(cmtData)
        };

        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

async function getCommentListFromServer(bno, page) {
    try {
        const resp = await fetch("/comment/"+bno+"/"+page);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}

//만약 page가 들어오는 값이 없다면 기본으로 1을 줌
function spreadCommentList(bno,page=1) {
    getCommentListFromServer(bno,page).then(result=>{
        console.log(result);
        const ul = document.getElementById('cmtListArea');
        //더보기 작업 이후로 result 안에 ph 값이 들어감
        if(result.cmtList.length>0) {
            if(page==1) {
                //page값이 올라가면 보여질 댓글 누적을 위해 초기화를 하지않음
                ul.innerHTML=''; 
            }
            for(let cvo of result.cmtList) {
                let li = `<li class="list-group-item" data-cno="${cvo.cno}">`;
                li+=`<div class="mb-3">no.${cvo.cno} | &nbsp;`;
                li+=`<div class="fw-bold">${cvo.writer}</div>${cvo.content}</div>`;
                li+=`<span class="badge rounded-pill text-bg-primary">${cvo.regDate}</span>`
                //수정, 삭제 버튼
                li+=`&nbsp;<button type="button" class="btn btn-sm btn-outline-warning mod" data-bs-toggle="modal" data-bs-target="#myModal">수정</button>`;
                li+=`&nbsp;<button type="button" data-cno="${cvo.cno}" class="btn btn-sm btn-outline-danger del">삭제</button>`;
                li+=`</li>`;
                ul.innerHTML+=li;
            }

            //더보기 버튼 작업
            let moreBtn = document.getElementById('moreBtn');
            console.log(moreBtn);
            //moreBtn 표시되는 조건
            //pgvo.pageNo = 1 / realEndPage=3
            //realEndPage보다 현재 내 페이지가 작으면 표시
            if(result.realEndPage>result.pgvo.pageNo) {
                moreBtn.style.visibility = 'visible';
                moreBtn.dataset.page = page+1;
            } else {
                moreBtn.style.visibility = 'hidden';
            }

        } else {
            let li = `<li class="list-group">Comment List Empty</li>`
            ul.innerHTML=li;
        }
    })
}

//수정 받는 비동기
async function getModCommentToServer(cmtModData) {
    try {
        const url="/comment/modify";
        const config ={
            method:"put",
            headers:{
                'content-type':'application/json; charset=utf-8'
            },
            body:JSON.stringify(cmtModData)
        };

        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

//삭제 받는 비동기
async function removeCommentFromServer(cnoVal) {
    try {
        const url = "/comment/"+cnoVal;
        const config = {
            method : "delete"
        }

        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

document.addEventListener('click',(e)=>{
    if(e.target.id=='moreBtn') {
        let page = parseInt(e.target.dataset.page);
        spreadCommentList(bnoVal, page);
    } else if(e.target.classList.contains('mod')){
        //내가 수정버튼을 누른 댓글의 li
        let li = e.target.closest('li');
        //nextSibling : 한 부모 안에서 다음 형제를 찾기
        let cmtText = li.querySelector('.fw-bold').nextSibling;
        console.log(cmtText)
        //값을 object(nod) 형태로 달고오기 때문에 nodeValue로 받아옴
        document.getElementById('cmtTextMod').value = cmtText.nodeValue;

        //수정 => 모달창(버튼)이나 li에 cno dataset으로 달기 / 필요값 : cno, content
        document.getElementById('cmtModBtn').setAttribute("data-cno", li.dataset.cno);
    }
    else if(e.target.id=='cmtModBtn') {
        let cmtModData = {
            cno:e.target.dataset.cno,
            content:document.getElementById('cmtTextMod').value
        };
        console.log(cmtModData);
        
        //비동기로 수정 요청
        getModCommentToServer(cmtModData).then(result=>{
            if(result=='1') {
                alert('댓글 수정');
                //모달창 닫기
                document.querySelector('.btn-close').click();
            } else{
                alert('수정 실패');
                document.querySelector('.btn-close').click();
            }
            spreadCommentList(bnoVal);
        })

    } 
    //삭제
    else if(e.target.classList.contains('del')){
        //cno를 들고오는 2가지 방법
        //let li = e.target.closest('li');
        //let cnoVal = li.dataset.cno;
        let cnoVal = e.target.dataset.cno;

        //비동기로 삭제 요청
        removeCommentFromServer(cnoVal).then(result=>{
            if(result=='1') {
                alert('댓글 삭제');
                spreadCommentList(bnoVal);
            }
        })
    }
})