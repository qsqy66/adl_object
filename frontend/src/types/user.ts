export interface User { userId: number; userName: string; userAccount: string; userPhone?: string }
export interface LoginRequest { username: string; password: string }
export interface UserForm { userName: string; userAccount: string; userPwd?: string; userPhone?: string }
