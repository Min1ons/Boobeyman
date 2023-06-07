RMMall 商城项目  

项目初始化步骤

1.安装nodejs环境,推荐使用v6.14.1
    下载地址 : https://nodejs.org/download/
    
2.安装yarn，并更换源  
    npm install -g yarn  
    yarn config set registry https://registry.npm.taobao.org/ -g  
    yarn config set sass_binary_site http://cdn.npm.taobao.org/dist/node-sass -g  
    

3.在项目根目录执行初始化
    yarn install  

4.启动项目
     yarn run dist   
