import { CanActivate, Injectable } from '@nestjs/common'

@Injectable()
export class StaffsGuard implements CanActivate {
  canActivate() {
    console.log('스태프 인증 완료.')
    return true
  }
}
