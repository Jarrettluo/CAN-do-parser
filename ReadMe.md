<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a id="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

[//]: # ([![Contributors][contributors-shield]][contributors-url])

[//]: # ([![Forks][forks-shield]][forks-url])

[//]: # ([![Stargazers][stars-shield]][stars-url])

[//]: # ([![Issues][issues-shield]][issues-url])

[//]: # ([![MIT License][license-shield]][license-url])

[//]: # ([![LinkedIn][linkedin-shield]][linkedin-url])



<!-- PROJECT LOGO -->
<br />
<div align="center">

[//]: # (  <a href="https://github.com/othneildrew/Best-README-Template">)

[//]: # (    <img src="images/logo.png" alt="Logo" width="80" height="80">)

[//]: # (  </a>)

<h3 align="center">CAN-Do-Parser</h3>

  <p align="center">
    CAN总线数据的解析工具。

[//]: # (    <br />)

[//]: # (    <a href="https://github.com/othneildrew/Best-README-Template"><strong>Explore the docs »</strong></a>)

[//]: # (    <br />)

[//]: # (    <br />)

[//]: # (    <a href="https://github.com/othneildrew/Best-README-Template">View Demo</a>)

[//]: # (    ·)

[//]: # (    <a href="https://github.com/othneildrew/Best-README-Template/issues/new?labels=bug&template=bug-report---.md">Report Bug</a>)

[//]: # (    ·)

[//]: # (    <a href="https://github.com/othneildrew/Best-README-Template/issues/new?labels=enhancement&template=feature-request---.md">Request Feature</a>)
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>目录</summary>
  <ol>
    <li>
      <a href="#about-the-project">关于项目</a>
    </li>
    <li>
      <a href="#getting-started">开始使用</a>
    </li>
    <li><a href="#license">开源许可</a></li>
    <li><a href="#contact">联系方式</a></li>
    <li><a href="#acknowledgments">致谢</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## 关于项目

`CAN-do-parser` 是用于CAN总线协议数据的解析工具。

主要目标是对字节数据根据DBC文件的信息解析为物理量。

主要的功能是：
- 解析`DBC`文件。
- 解析字节数组`byte[]`为十进制数据。

<p align="right">(<a href="#readme-top">返回</a>)</p>


<!-- GETTING STARTED -->
## 开始使用

### 安装依赖

```xml
<dependency>
  <groupId>io.github.jarrettluo</groupId>
  <artifactId>can-frame-parser</artifactId>
  <version>1.0.0</version>
</dependency>
```

### 解析dbc文件

```java

String filePath = "xxx.dbc";

// 对dbc文件进行解析
Map<DbcMessage, List<DbcSignal>> dbcMessageListMap = DbcParser.parseFile(filePath);

```

### 解析CAN帧数据

#### 方法1： 通过实例化解析对象进行解析数据帧
```java
// CAN 帧数据
CANFrame canFrame = new CANFrame(System.currentTimeMillis(), 1, 8, 20, "275d602702000000");

// dbc数据; 通过dbc解析器得到
Map<String, DbcMessage> dbcMessageMap = new HashMap<String, DbcMessage>();

// 初始化解析器
CANFrameParser canFrameParser = new CANFrameParser(dbcMessageMap);

// 对can数据进行解析
Map<String, Double> physicalValueMap = canFrameParser.extractMessage(canFrame);

```

### 方法2： 通过解析工具静态方法进行解析某一条信号
```java
CANFrame canFrame = new CANFrame(System.currentTimeMillis(), 1, 8, 20, "275d602702000000");

// 解析具体的信号
int startBit = 32;
int length = 32;
boolean isSigned = true;
boolean isLittleEndian = true;
String factor = "1";
String offset = "0";

// 解析工具的静态解析方法，对信号进行解析
double signal = CANFrameParser.extractSignal(canFrame.getMsgData(), startBit, length, isSigned, 
                isLittleEndian, factor, offset);

```

<!-- LICENSE -->
## 开源许可

请遵守开源协议，查看更多详细信息可查看`LICENSE.txt`。

<p align="right">(<a href="#readme-top">返回</a>)</p>


<!-- CONTACT -->
## 联系方式

Jarrett Luo - [http://jiaruiblog.com](http://jiaruiblog.com) - luojiarui2@163.com


<p align="right">(<a href="#readme-top">返回</a>)</p>


<!-- ACKNOWLEDGMENTS -->
## 致谢

暂无。

<p align="right">(<a href="#readme-top">返回</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=for-the-badge
[contributors-url]: https://github.com/othneildrew/Best-README-Template/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/othneildrew/Best-README-Template/network/members
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/othneildrew/Best-README-Template/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/othneildrew/Best-README-Template/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/othneildrew/Best-README-Template/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/othneildrew
[product-screenshot]: images/screenshot.png
[Next.js]: https://img.shields.io/badge/next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white
[Next-url]: https://nextjs.org/
[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://reactjs.org/
[Vue.js]: https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D
[Vue-url]: https://vuejs.org/
[Angular.io]: https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white
[Angular-url]: https://angular.io/
[Svelte.dev]: https://img.shields.io/badge/Svelte-4A4A55?style=for-the-badge&logo=svelte&logoColor=FF3E00
[Svelte-url]: https://svelte.dev/
[Laravel.com]: https://img.shields.io/badge/Laravel-FF2D20?style=for-the-badge&logo=laravel&logoColor=white
[Laravel-url]: https://laravel.com
[Bootstrap.com]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[Bootstrap-url]: https://getbootstrap.com
[JQuery.com]: https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white
[JQuery-url]: https://jquery.com 
