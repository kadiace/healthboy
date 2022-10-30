import { Injectable } from '@nestjs/common';

@Injectable()
export class GymsService {
  create() {
    return 'This action adds a new gym';
  }

  findAll() {
    return `This action returns all gyms`;
  }

  findOne(id: number) {
    return `This action returns a #${id} gym`;
  }

  update(id: number) {
    return `This action updates a #${id} gym`;
  }

  remove(id: number) {
    return `This action removes a #${id} gym`;
  }
}
