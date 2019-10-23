let currentPage=0;
let todoList = [];
let lastResponseEmpty=false;
const pageSize=20;
async function initializeData(){
    try{
        let response= await fetch('/todo/create/20',{
             method: 'POST',
             headers: {
               'Content-Type': 'application/json;charset=utf-8'
             }
          }
        )
        if (response.status != 201){
            showHide('errorBlock',`Failed to Initialise Todo List Reason : ${await response.json().message}.message)}`)
        }else{
            let resp=await response.json();
            if (resp.length>0){
                todoList=todoList.concat(resp);
                currentPage++;
                show();
            }else{
                showHide('errorBlock',`Failed to Initialize ToDo list Automatically `)
            }
        }
    }catch(error){
        showHide('errorBlock',`Failed to get ToDo list error is :  ${error}`)
    }
}
async function get_todos(page) {
    try{
        let getResponse= await fetch(`/todo?page=${page}&size=${pageSize}`)
        if (getResponse.status != 200){
            showHide('errorBlock',`Failed to get Todo List Reason : ${await getResponse.json().message}.message)}`)
        }else{
            let resp=await getResponse.json();
            if (resp.length>0){
                todoList=todoList.concat(resp);
                show();
            }else{
                lastResponseEmpty=true;
                currentPage== 0? initializeData():null;
            }
        }
    }catch(error){
        showHide('errorBlock',`Failed to get ToDo list error is :  ${error}`)
    }
}

async function add() {
    let task = document.getElementById('task').value;
    try{
        let postResponse= await fetch('/todo/create/20',{
                     method: 'POST',
                     headers: {
                       'Content-Type': 'text/plain'
                     },
                     body: task
                  }
        )
        if (postResponse.status != 201){
            console.log(` Reason : ${xhttp.responseText.message}`);
            showHide('errorBlock',`Failed to add Todo " ${task} "to the List.`);
        }else{
            addTodoToList(await postResponse.json());
            showHide('successBlock',"Todo added");
        }
    }catch(error){
        showHide('errorBlock',`Failed to Add ToDo ${task} to the list  error is :  ${error}`)
    }
}

async function remove(id) {
    try{
        let deleteResponse= await fetch(`/todo/${id}`,{method: 'DELETE'})
        if (deleteResponse.status != 202){
            console.log(` Delete failed Reason : ${await deleteResponse.json().message}`);
            showHide('errorBlock',`Failed to Remove Todo.`);
        }else{
            todoList=todoList.filter(item=>item.id!=id);
            show();
        }
    }catch(error){
        showHide('errorBlock',`Failed to delete ToDo element error is :  ${error}`)
    }
}

async function showHide(id,error){
    document.getElementById(id).className = 'show';
    document.getElementById(id).innerHTML=error;
    setTimeout(function(){
            document.getElementById(id).className = 'hide';
        }, 2500);
}

async function changeTodoStatus(id){
    let ele=todoList.find((item)=>item.id==id)
    ele.status= ele.status=="DONE"?"NOTDONE":"DONE";
    xhttp.open("PUT", "/todo/"+id, false);
    try{
        xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        xhttp.send(JSON.stringify(ele));
        let putResponse= await fetch(`/todo/${id}`,{
                             method: 'PUT',
                             headers: {
                               'Content-Type': 'application/json;charset=UTF-8'
                             },
                             body: ele
                          }
                )
        if (putResponse.status != 200){
            console.log(` failed to Update Todo status Reason : ${await putResponse.json().message}`);
            showHide('errorBlock',`Failed to Update Todo.`);
        }
    }catch(error){
        showHide('errorBlock',`failed to Update Todo status :  ${error}`)
    }
}
function addTodoToList(todo){
    let listWithCheckBox='<li><input type="checkbox" onChange="changeTodoStatus('+todo.id;
    let checkbox_part2=')" class="checkbox" id="'+todo.id+'"';
    let checked=todo.status=="NOTDONE"?'':' checked="checked"';
    let label = '><label for="'+todo.id+'">'+todo.description+'</label>';
    let deleteButton='<button class="remove" onclick="remove('+todo.id+')" id="' + todo.id  + '">Delete</button></li>';
    document.getElementById('todolist').insertAdjacentHTML("beforeend",listWithCheckBox+checkbox_part2+checked+label+deleteButton);
}
function show() {
    document.getElementById('todolist').innerHTML='';
    for(let i=0; i<todoList.length; i++) {
        addTodoToList(todoList[i]);
    };
}
function onScroll() {
     let top=document.getElementById('todos').scrollTop;
     let sizeofDiv=document.getElementById('todos').offsetHeight;
     let scrolledHeight=document.getElementById('todos').scrollHeight;
     if (top>=scrolledHeight-sizeofDiv){
        lastResponseEmpty? null:currentPage++;
        get_todos(currentPage)
     }
};
document.getElementById('todos').addEventListener('scroll', onScroll);
get_todos(currentPage)