import { CanActivate, Injectable } from '@nestjs/common'

@Injectable()
export class OwnersGuard implements CanActivate {
  canActivate() {
    console.log('사업주 인증 완료.')
    return true
  }
}
