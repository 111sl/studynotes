day01

1.adb命令：install、pull、push

​	adb install 安装包：向设备安装apk，通过选项 -r 可覆盖安装。<br/>	adb pull 手机目录  目标目录：从手机拉取文件到电脑，一些目录需要权限，如etc、system、dev等，一些目录不需要权限，如sdcard。<br/>	adb push 目标目录  手机目录：从电脑向手机推送文件，和pull一样部分需要权限。

2.git命令：status、branch、merge、checkout

​	git status： 列出当前目录所有还没有被git管理的文件和被git管理且被修改但还未提交(git commit)的文件。 <br/>	git branch 分支名：创建分支<br/>	git branch：查看分支<br/>	git merge 分支名：将输入的分支合并到当前分支<br/>	git checkout 分支名：切换分支<br/>

​	