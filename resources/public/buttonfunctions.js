function download(filename, text) {
    var element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
    element.setAttribute('download', filename);

    element.style.display = 'none';
    document.body.appendChild(element);

    element.click();

    document.body.removeChild(element);
}

function shadeBlocks() {
    Blockly.mainWorkspace.getAllBlocks().forEach(function(b) {
        //b.setMovable(false);
        //b.setEditable(true);
        b.setShadow(true);

        b.inputList.forEach(il => {
            il.fieldRow.forEach(fr => {
                fr.textElement_.classList.replace("blocklyText", "klmtext");
            })})
    });
}

function unshadeBlocks() {
    Blockly.mainWorkspace.getAllBlocks().forEach(function(b) {
        //b.setMovable(false);
        //b.setEditable(true);

        b.inputList.forEach(il => {
            il.fieldRow.forEach(fr => {
                fr.textElement_.classList.replace("klmtext", "blocklyText");
            })
        })

        b.setShadow(false);
    });
}

function loadFileAsText() {
    //document.getElementById("fileToLoad").disabled=true;
    var fileToLoad = document.getElementById("fileToLoad").files[0];
    var fileReader = new FileReader();
    fileReader.onload = function(fileLoadedEvent) {
        var textFromFileLoaded = fileLoadedEvent.target.result;
        Blockly.Xml.domToWorkspace(Blockly.Xml.textToDom(textFromFileLoaded), Blockly.mainWorkspace);
        Blockly.mainWorkspace.scrollbar.set(450, Blockly.mainWorkspace.getMetrics().contentHeight - 500);
    };
    fileReader.readAsText(fileToLoad, "UTF-8");
}

function hasShaded() {
    s = false;
    Blockly.mainWorkspace.getAllBlocks().forEach(function(b) {
        if (b.isShadow()) {
            s = true;
        }
    })
    return (s);
}

function saveXML() {
    if (hasShaded()) {
        alert("Shadow Blocks cannot be saved. Press Unshade Blocks Button.");
    } else {
        download('workspace.xml', Blockly.Xml.domToPrettyText(Blockly.Xml.workspaceToDom(Blockly.mainWorkspace)));
    }
}

function sendXMLToServer() {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", "thexml");
    var xmlDoc;
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            xmlDoc = xmlhttp.responseXML;
            console.log(xmlDoc);
        }
    };
    xmlhttp.setRequestHeader('Content-Type', 'text/xml');
    var xml = Blockly.Xml.domToPrettyText(Blockly.Xml.workspaceToDom(Blockly.mainWorkspace));
    xmlhttp.send(xml);
}
