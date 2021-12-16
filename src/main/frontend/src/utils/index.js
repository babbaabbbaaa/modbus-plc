
function download(res) {
  let fileName;
    try{
      fileName =  res.headers['content-disposition']&&res.headers['content-disposition'].split(';')[1].split('=')[1];
      fileName = fileName.substr(7);
      fileName = decodeURIComponent(decodeURIComponent(fileName));
    }catch{
      // fileName =new Date().getTime() ;
      // message.error('file name is empty');
      console.log('file name is empty');
    }
    let fileArr =  fileName&&fileName.split('.');
    let extensionName =  fileArr&&fileArr[fileArr.length-1];
    let blob;
    if(extensionName ==='zip'){
      blob = new Blob([res.data])
    }else{
      blob = new Blob([res.data], { type: 'application/vnd.ms-excel;charset=utf-8' });
    }
    let url = window.URL.createObjectURL(blob);
    let aLink = document.createElement("a");
    aLink.style.display = "none";
    aLink.href = url;
    aLink.setAttribute("download", fileName);
    document.body.appendChild(aLink);
    aLink.click();
    document.body.removeChild(aLink);
    window.URL.revokeObjectURL(url);
}

export {
  download
}