var currentPage=0;
var todoList = [];
var lastResponseEmpty=false;
var xhttp =new XMLHttpRequest();
function initializeData(){
    try{
        xhttp.open("POST", '/todo/create/20', false);
        xhttp.send();
        if (xhttp.status != 201){
            showError(`Failed to Initialise Todo List Reason : ${JSON.parse(xhttp.responseText.message)}`)
        }else{
            var resp=JSON.parse(xhttp.responseText);
            if (resp.length>0){
                todoList=todoList.concat(resp);
                currentPage++;
                show();
            }else{
                showError(`Failed to Initialize ToDo list Automatically `)
            }
        }
    }catch(error){
        showError(`Failed to get ToDo list error is :  ${error}`)
    }
}
function get_todos(page) {
    xhttp.open("GET", '/todo?page='+page+'&size=20', false);
    try{
        xhttp.send();
        if (xhttp.status != 200){
            showError(`Failed to get Todo List Reason : ${JSON.parse(xhttp.responseText.message)}`)
        }else{
            var resp=JSON.parse(xhttp.responseText);
            if (resp.length>0){
                todoList=todoList.concat(resp);
                show();
            }else{
                lastResponseEmpty=true;
                currentPage== 0? initializeData():null;
            }
        }
    }catch(error){
        showError(`Failed to get ToDo list error is :  ${error}`)
    }
}

function add() {
    var task = document.getElementById('task').value;
    xhttp.open("POST", "/todo", false);
    try{
        xhttp.setRequestHeader("Content-Type", "text/plain");
        xhttp.send(task);
        if (xhttp.status != 201){
            console.log(` Reason : ${xhttp.responseText.message}`);
            showError(`Failed to add Todo " ${task} "to the List.`);
        }else{
            addTodoToList(JSON.parse(xhttp.responseText));
            showSuccess("Todo added");
        }
    }catch(error){
        showError(`Failed to Add ToDo ${task} to the list  error is :  ${error}`)
    }
}

function remove(id) {
    xhttp.open("DELETE", "/todo/"+id, false);
    try{
        xhttp.send();
        if (xhttp.status != 202){
            console.log(` Delete failed Reason : ${JSON.parse(xhttp.responseText.message)}`);
            showError(`Failed to Remove Todo.`);
        }else{
            todoList=todoList.filter(item=>item.id!=id);
            show();
        }
    }catch(error){
        showError(`Failed to delete ToDo element error is :  ${error}`)
    }
}
function showSuccess(message){
document.getElementById('successBlock').className = 'show';
document.getElementById('successBlock').innerHTML=message;
setTimeout(function(){
    document.getElementById('successBlock').className = 'hide';
}, 2500);
}
function showError(error){
document.getElementById('errorBlock').className = 'show';
document.getElementById('errorBlock').innerHTML=error;
setTimeout(function(){
    document.getElementById('errorBlock').className = 'hide';
}, 2500);
}

function changeTodoStatus(id){
    var ele=todoList.find((item)=>item.id==id)
    ele.status= ele.status=="DONE"?"NOTDONE":"DONE";
    xhttp.open("PUT", "/todo/"+id, false);
    try{
        xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        xhttp.send(JSON.stringify(ele));
        if (xhttp.status != 200){
            console.log(` failed to Update Todo status Reason : ${JSON.parse(xhttp.responseText.message)}`);
            showError(`Failed to Remove Todo.`);
        }
    }catch(error){
        showError(`failed to Update Todo status :  ${error}`)
    }
}
function addTodoToList(todo){
    var listWithCheckBox='<li><input type="checkbox" onChange="changeTodoStatus('+todo.id;
    var checkbox_part2=')" class="checkbox" id="'+todo.id+'"';
    var checked=todo.status=="NOTDONE"?'':' checked="checked"';
    var label = '><label for="'+todo.id+'">'+todo.description+'</label>';
    var deleteButton='<button class="remove" onclick="remove('+todo.id+')" id="' + todo.id  + '">Delete</button></li>';
    document.getElementById('todolist').insertAdjacentHTML("beforeend",listWithCheckBox+checkbox_part2+checked+label+deleteButton);
}
function show() {
    document.getElementById('todolist').innerHTML='';
    for(var i=0; i<todoList.length; i++) {
        addTodoToList(todoList[i]);
    };
}
function onScroll() {
     var top=document.getElementById('todos').scrollTop;
     var sizeofDiv=document.getElementById('todos').offsetHeight;
     var scrolledHeight=document.getElementById('todos').scrollHeight;
     if (top>=scrolledHeight-sizeofDiv){
        lastResponseEmpty? null:currentPage++;
        get_todos(currentPage)
     }
};
document.getElementById('todos').addEventListener('scroll', onScroll);
get_todos(currentPage)