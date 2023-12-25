import { Injectable } from '@nestjs/common'

@Injectable()
export class ReservationsService {
  create() {
    return 'This action adds a new reservation'
  }

  findAll() {
    return `This action returns all reservations`
  }

  findOne(id: number) {
    return `This action returns a #${id} reservation`
  }

  update(id: number) {
    return `This action updates a #${id} reservation`
  }

  remove(id: number) {
    return `This action removes a #${id} reservation`
  }
}
