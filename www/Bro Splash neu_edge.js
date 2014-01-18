/**
 * Adobe Edge: symbol definitions
 */
(function($, Edge, compId){
//images folder
var im='images/';

var fonts = {};
   fonts['basic, sans-serif']='<script src=\"http://use.edgefonts.net/basic:n4:all.js\"></script>';
   fonts['droid-sans, sans-serif']='<script src=\"http://use.edgefonts.net/droid-sans:n4,n7:all.js\"></script>';
   fonts['source-sans-pro, sans-serif']='<script src=\"http://use.edgefonts.net/source-sans-pro:n4,n9,n7,i7,i4,n3,i3,n6,i6,i9,n2,i2:all.js\"></script>';


var resources = [
];
var symbols = {
"stage": {
   version: "2.0.1",
   minimumCompatibleVersion: "2.0.0",
   build: "2.0.1.268",
   baseState: "Base State",
   initialState: "Base State",
   gpuAccelerate: false,
   resizeInstances: false,
   content: {
         dom: [
         {
            id:'Text',
            type:'text',
            rect:['17.7%','7.5%','18%','26.1%','auto','auto'],
            text:"Bro",
            align:"center",
            font:['source-sans-pro, sans-serif',[300,"%"],"rgba(254,254,254,1.00)","200","none",""]
         },
         {
            id:'TextCopy',
            type:'text',
            rect:['17.7%','7.5%','47.2%','7.8%','auto','auto'],
            text:"Get Bro &amp; Stay Bro",
            align:"center",
            font:['source-sans-pro, sans-serif',[300,"%"],"rgba(254,254,254,1.00)","100","none",""]
         },
         {
            id:'Text2',
            type:'text',
            rect:['13.6%','-20.7%','57.9%','9.7%','auto','auto'],
            text:"COMING SOON",
            align:"center",
            font:['source-sans-pro, sans-serif',[100,"%"],"rgba(255,255,255,1.00)","700","none","normal"],
            filter:[0,0,1,1,0,0,0,0,"rgba(0,0,0,0)",0,0,0],
            transform:[[],['25']]
         },
         {
            id:'Logo_Bro2',
            type:'image',
            rect:['319px','99px','309px','314px','auto','auto'],
            opacity:0,
            fill:["rgba(0,0,0,0)",im+"Logo%20Bro2.png",'0px','0px']
         }],
         symbolInstances: [

         ]
      },
   states: {
      "Base State": {
         "${_Logo_Bro2}": [
            ["style", "top", '99px'],
            ["style", "height", '314px'],
            ["style", "opacity", '0'],
            ["style", "left", '319px'],
            ["style", "width", '309px']
         ],
         "${_Text2}": [
            ["subproperty", "filter.contrast", '1'],
            ["transform", "rotateZ", '25deg'],
            ["color", "color", 'rgba(255,255,255,1.00)'],
            ["style", "opacity", '0'],
            ["style", "left", '39.32%'],
            ["style", "font-size", '150%'],
            ["style", "top", '13.16%'],
            ["style", "height", '9.64%'],
            ["style", "width", '57.87%'],
            ["style", "font-weight", '700']
         ],
         "${_Text}": [
            ["style", "letter-spacing", '0em'],
            ["style", "width", '18%'],
            ["color", "color", 'rgba(254,254,254,1.00)'],
            ["style", "opacity", '0'],
            ["style", "left", '43.56%'],
            ["style", "font-size", '700%'],
            ["style", "top", '59.22%'],
            ["style", "overflow", 'visible'],
            ["style", "text-align", 'center'],
            ["style", "text-indent", '0%'],
            ["style", "height", '13.34%'],
            ["style", "font-family", 'source-sans-pro, sans-serif'],
            ["style", "word-spacing", '0em'],
            ["style", "font-weight", '200']
         ],
         "${_Stage}": [
            ["color", "background-color", 'rgba(255,255,255,0.00)'],
            ["style", "overflow", 'hidden'],
            ["style", "height", '655px'],
            ["style", "width", '900px']
         ],
         "${_TextCopy}": [
            ["style", "letter-spacing", '0em'],
            ["style", "font-weight", '100'],
            ["color", "color", 'rgba(254,254,254,1.00)'],
            ["style", "opacity", '0'],
            ["style", "left", '29%'],
            ["style", "font-size", '150%'],
            ["style", "top", '80.77%'],
            ["style", "text-indent", '0%'],
            ["style", "text-align", 'center'],
            ["style", "overflow", 'visible'],
            ["style", "height", '7.93%'],
            ["style", "font-family", 'source-sans-pro, sans-serif'],
            ["style", "word-spacing", '0em'],
            ["style", "width", '47.12%']
         ]
      }
   },
   timelines: {
      "Default Timeline": {
         fromState: "Base State",
         toState: "",
         duration: 6000,
         autoPlay: true,
         timeline: [
            { id: "eid30", tween: [ "style", "${_Text2}", "opacity", '1', { fromValue: '0'}], position: 5000, duration: 1000, easing: "easeInCirc" },
            { id: "eid65", tween: [ "style", "${_Text2}", "font-size", '150%', { fromValue: '150%'}], position: 6000, duration: 0, easing: "easeInCirc" },
            { id: "eid112", tween: [ "style", "${_TextCopy}", "font-size", '150%', { fromValue: '150%'}], position: 6000, duration: 0 },
            { id: "eid90", tween: [ "style", "${_Text2}", "top", '13.16%', { fromValue: '13.16%'}], position: 6000, duration: 0, easing: "easeInCirc" },
            { id: "eid84", tween: [ "style", "${_Text}", "height", '13.34%', { fromValue: '13.34%'}], position: 6000, duration: 0, easing: "easeInCirc" },
            { id: "eid109", tween: [ "style", "${_TextCopy}", "top", '80.77%', { fromValue: '80.77%'}], position: 6000, duration: 0 },
            { id: "eid23", tween: [ "style", "${_TextCopy}", "height", '7.93%', { fromValue: '7.93%'}], position: 5000, duration: 0, easing: "easeInCirc" },
            { id: "eid22", tween: [ "style", "${_TextCopy}", "opacity", '1', { fromValue: '0'}], position: 4000, duration: 1000, easing: "easeInCirc" },
            { id: "eid4", tween: [ "style", "${_Text}", "opacity", '1', { fromValue: '0'}], position: 2500, duration: 1000, easing: "easeInCirc" },
            { id: "eid107", tween: [ "style", "${_Text}", "top", '59.22%', { fromValue: '59.22%'}], position: 6000, duration: 0 },
            { id: "eid89", tween: [ "style", "${_Text2}", "left", '39.32%', { fromValue: '39.32%'}], position: 6000, duration: 0, easing: "easeInCirc" },
            { id: "eid108", tween: [ "style", "${_TextCopy}", "left", '29%', { fromValue: '29%'}], position: 6000, duration: 0 },
            { id: "eid105", tween: [ "style", "${_Text}", "font-size", '700%', { fromValue: '700%'}], position: 6000, duration: 0 },
            { id: "eid114", tween: [ "style", "${_Logo_Bro2}", "opacity", '1', { fromValue: '0'}], position: 0, duration: 2000 },
            { id: "eid106", tween: [ "style", "${_Text}", "left", '43.56%', { fromValue: '43.56%'}], position: 6000, duration: 0 }         ]
      }
   }
}
};


Edge.registerCompositionDefn(compId, symbols, fonts, resources);

/**
 * Adobe Edge DOM Ready Event Handler
 */
$(window).ready(function() {
     Edge.launchComposition(compId);
});
})(jQuery, AdobeEdge, "EDGE-45210950");
