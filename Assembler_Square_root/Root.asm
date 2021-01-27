%macro pushd 0
    push edx
    push ecx
    push ebx
    push eax
%endmacro

%macro popd 0
    pop eax
    pop ebx
    pop ecx
    pop edx
%endmacro

%macro print 2
    pushd
    
    mov edx, %1
    mov ecx, %2
    mov ebx, 1
    mov eax, 4
    int 0x80
   
    popd
%endmacro

%macro nl 0
    print nlen, newline
%endmacro

%macro dprint 0
    pushd

    mov ecx, 10
    mov bx, 0

    %%_divide:
        mov edx, 0
        div ecx
        push dx
        inc bx
    test eax, eax
    jnz %%_divide

    mov cx, bx

    %%_digit:
        pop ax
        add ax, '0'
        mov [count], ax
        print 1, count
        dec cx
        mov ax, cx
    cmp cx, 0
    jg %%_digit

    nl

    popd
%endmacro

%macro sqrt 1
    pushd

    mov eax, %1
    mov ecx, 2
    mov edx, 0
    div ecx
    mov [x1], eax

    calc_x2 %1

    %%_while:
        mov eax, [x2]
        mov [x1], eax
        calc_x2 %1

        mov eax, [x1]
        mov ecx, [x2]
        sub eax, ecx
    cmp eax, 1
    jge %%_while
    
    popd
%endmacro

%macro calc_x2 1 ;
    pushd

    mov eax, %1
    mov ecx, [x1] 
    mov edx, 0
    div ecx

    add eax, [x1]

    mov ecx, 2
    mov edx, 0
    div ecx

    mov [x2], eax

    popd
%endmacro

section .text

global _start

_start:
    sqrt 625
    mov eax, [x2]
    dprint

    mov eax, 1
    int 0x80

section .data
    newline db 0xA, 0xD
    nlen equ $ - newline

section .bss
    count resd 1
    x1 resd 1
    x2 resd 1