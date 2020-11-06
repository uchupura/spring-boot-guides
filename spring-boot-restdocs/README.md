# Asciidoc

## 줄바꿈 (line break)
문단 안에서도 줄바꿈을 쓰고 싶을때가 있습니다. 줄 끝에 +를 붙이면 줄바꿈이 됩니다.  
소스
```shell script
Rubies are red, +
Topazes are blue.
```
출력
```shell script
Rubies are red,
Topazes are blue.
```
  
또는 문단 앞에 hardbreaks 속성을 줄어도 됩니다.  
소스
```shell script
[%hardbreaks]
Ruby is red.
Java is black.
```
출력
```shell script
Ruby is red.
Java is black.
```

문서 전체에서 소스의 줄바꿈을 출력의 줄바꿈으로 사용하고 싶다면 문서에 hardbreaks 속성을 부여하면 됩니다.
```shell script
= Line Break Doc Title
:hardbreaks:

Rubies are red,
Topazes are blue.
```

## 경고
사용자의 주의를 이끌어 내기 위해 경고를 쓰고 싶을때가 있습니다. 이를 위해 다음과 같은 경고구가 준비되어 있습니다.
* NOTE
* TIP
* IMPORTANT
* CAUTION
* WARNING

경고를 주고 싶을때 새로운 문단을 시작하고 대문자로 경고 라벨을 기술한후에 :를 붙이고 한칸 띄고 내용을 기술하면 됩니다.  
소스
```shell script
NOTE: 참고하고 하세요

TIP: 팁입니다

IMPORTANT: 중요합니다.

CAUTION: 주의 하세요

WARNING: 위험합니다.
```
문서에 icons 속성을 설정을 이용해 아이콘을 사용할수도 있습니다.

### Text formating
아스키독은 문장을 강조하기 위해 Quoted text를 사용합니다.  
소스
```shell script
bold : *bold phrase* & **char**acter**s**

italic : _italic phrase_ & __char__acter__s__

bold & italic : *_bold italic phrase_* & **__char__**acter**__s__**

monospace : `monospace phrase` & ``char``acter``s``

monospace & bold : `*monospace bold phrase*` & ``**char**``acter``**s**``

monospace & italic : `_monospace italic phrase_` & ``__char__``acter``__s__``

monospace & bold & italic : `*_monospace bold italic phrase_*` &
``**__char__**``acter``**__s__**``
```

### 목록(List)
#### 순서가 없는 목록
목록을 표시하고 싶다면 문단을 새로 시작하고 * 또는 - 로 시작하는 문장을 작성하면 됩니다.  
소스
```shell script
* Edgar Allen Poe
* Sheri S. Tepper
* Bill Bryson
```
목록에 제목을 기입하고 싶다면 .으로 시작하는 제목을 기입한후에 목록을 작성하면 됩니다. 이때 공백이 없어야 합니다. 있다면 추후 순서형 목록과 혼돈이 발생할수 있습니다.
```shell script
.Kizmet's Favorite Authors
* Edgar Allen Poe
* Sheri S. Tepper
* Bill Bryson
```

#### 순서를 가지는 목록
숫자가 아직 결정 않되었거나, 자동으로 넣고 싶다면 .으로 시작하면 됩니다.
```shell script
. Protons
. Electrons
. Neutrons
```

.으로 시작하는 목록에서 새로운 번호에서 시작하고 싶다면 start 속성을 주면 됩니다.  
소스
```shell script
[start=4]
 . Step four
 . Step five
 . Step six
```

출력
```shell script
4.Step four
5.Step five
6.Step six
```

#### 이름 붙은 목록(Labeled list)
항목에 대한 설명을 위해서 자주 사용됩니다.
* 구분자
* 공백
* 아이템

으로 구성됩니다.

소스
```shell script
CPU:: The brain of the computer.
Hard drive:: Permanent storage for operating system and/or user files.
RAM:: Temporarily stores information the CPU uses during operation.
Keyboard:: Used to enter text or control items on the screen.
Mouse:: Used to point to and select items on your computer screen.
Monitor:: Displays information in visual form using text and graphics.
```

출력
```shell script
CPU
  The brain of the computer.

Hard drive
  Permanent storage for operating system and/or user files.

RAM
  Temporarily stores information the CPU uses during operation.

Keyboard
  Used to enter text or control items on the screen.

Mouse
  Used to point to and select items on your computer screen.

Monitor
  Displays information in visual form using text and graphics.
```

하위에 목록을 추가할수 있습니다.
소스
```shell script
Dairy::
* Milk
* Eggs
Bakery::
* Bread
Produce::
* Bananas
```

출력
```shell script
Dairy
  * Milk
  * Eggs 
Bakery
  * Bread
Produce
  * Bananas
```