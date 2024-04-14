import { CanActivate, Injectable } from '@nestjs/common'

@Injectable()
export class MembersGuard implements CanActivate {
  canActivate() {
    console.log('회원 인증 완료.')
    return true
  }
}
