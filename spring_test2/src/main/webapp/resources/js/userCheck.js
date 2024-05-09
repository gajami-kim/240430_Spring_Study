console.log("user check in");

let check=0;

document.getElementById('cheBtn').addEventListener('click',()=>{
    //check=1;
    const email = document.getElementById('email').value;
   
    console.log(email);
    if(email!='') {
    	emailCheck(email).then(result=>{
        	console.log(result);
            if(result==1) {
                alert("사용가능한 아이디입니다");
                check+=1;
            } else {
                alert("중복된 아이디가 있습니다.");
                check+=0;
                document.getElementById('email').value ='';
                document.getElementById('email').focus();
                // document.getElementById('joinBtn').disabled=true;
            }
            console.log(check);
            disabledBtn(check);
        })
    } else {
        alert('이메일을 입력해주세요');
        document.getElementById('email').focus();
    }
})

document.getElementById('cheNickBtn').addEventListener('click',()=>{
    console.log('nickname check btn click');
    const nickName = document.getElementById('nickName').value;
    if(nickName!=''){
        nickNameCheck(nickName).then(result=>{
            if(result==1) {
                alert("사용가능한 닉네임입니다");
                check+=1;
                console.log(check);
            } else {
                alert("중복된 닉네임이 있습니다.")
                check+=0;
                document.getElementById('nickName').value ='';
                document.getElementById('nickName').focus();
            }
            
            console.log(check);
            disabledBtn(check);
        });
    } else {
        alert('닉네임을 입력해주세요');
        document.getElementById('nickName').focus();
    }
})

async function emailCheck(email){
    try {
        const url = "/user/"+email;
        const config = {
            method:"post"
        }

        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

async function nickNameCheck(nickName) {
    try {
        const url = "/user/nick/"+nickName;
        const config = {
            method : "post"
        }

        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

function disabledBtn(check){
    if(check==2) {
        document.getElementById('joinBtn').disabled=false;
    } else {
        document.getElementById('joinBtn').disabled=true;
    }
}