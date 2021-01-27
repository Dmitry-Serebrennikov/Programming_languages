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

%macro avg_array 2
    pushd
    
    mov eax, 0
    mov bx, 0
    
    %%_loop:
        add eax, [%1+ebx]
        add bx, 4
    cmp bx, alen
    jne %%_loop

    mov [%2], eax
    
    mov eax, alen
    mov ecx, 4
    mov edx, 0
    div ecx
    
    mov ecx, eax
    mov eax, [%2]
    mov edx, 0
    div ecx
    
    mov [%2], eax
    
    popd
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
    
    popd
%endmacro

section .text

global _start

_start:
    avg_array x, num1
    mov eax, [num1]
    dprint
 
    nl
    
    avg_array y, num2
    mov eax, [num2]
    dprint
    
    nl
    
    mov eax, [num1]
    mov ecx, [num2]
    
    sub eax, ecx
    
    cmp eax, 0
    jge _NOT_NEGATIVE
    
    _NEGATIVE:
        print len, message 
        mov eax, [num2]
        mov ecx, [num1]
    
        sub eax, ecx
    _NOT_NEGATIVE:
        dprint
    
    mov eax, 1
    int 0x80

section .data
    x dd 5, 3, 2, 6, 1, 7, 4
    alen equ $ - x
    y dd 0, 10, 1, 9, 2, 8, 5
    
    message db "-"
    len equ $ - message
    newline db 0xA, 0xD
    nlen equ $ - newline

section .bss
    count resd 1
    num1 resd 1
    num2 resd 1