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
                let li = `<li class="list-group-item">`;
                li+=`<div class="input-group mb-3">no.${cvo.cno} | &nbsp;`;
                li+=`<div class="fw-bold">${cvo.writer}</div><br>${cvo.content}</div>`;
                li+=`<span class="badge rounded-pill text-bg-primary">${cvo.regDate}</span>`
                //수정, 삭제 버튼
                li+=`&nbsp;<button type="button" class="btn btn-sm btn-outline-warning mod" data-bs-toggle="modal" data-bs-target="#myModal">수정</button>`;
                li+=`&nbsp;<button type="button" class="btn btn-sm btn-outline-danger del">삭제</button>`;
                li+=`</li>`;
                ul.innerHTML+=li;
            }

            //더보기 버튼 작업
            let moreBtn = document.getElementById('moreBtn');
            console.log(moreBtn);
            //moreBtn 표시되는 조건

        } else {
            let li = `<li class="list-group">Comment List Empty</li>`
            ul.innerHTML=li;
        }
    })
}

document.addEventListener('click',(e)=>{
    if(e.target.id=='moreBtn') {
        let page = parseInt(e.target.dataset.page);
        spreadCommentList(bnoVal, page);
    }
})